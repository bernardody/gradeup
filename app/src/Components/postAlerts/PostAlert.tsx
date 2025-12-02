import { useEffect, useState } from "react";
import "./PostAlert.css";

interface Subject {
  id: number;
  name: string;
}

interface Trimester {
  id: number;
  name: string;
}

interface Exam {
  id: number;
  name: string;
  subject: Subject;
  trimester: Trimester;
}

export default function PostAlert({ classId }: { classId: number }) {
  const [exams, setExams] = useState<Exam[]>([]);
  const [loading, setLoading] = useState(true);

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [selectedExamId, setSelectedExamId] = useState<string>("");

  useEffect(() => {
    if (!classId) {
      setLoading(false);
      return;
    }

    const token = localStorage.getItem("token");
    
    if (!token) {
      console.error("Token não encontrado");
      setLoading(false);
      return;
    }

    const controller = new AbortController();
    const signal = controller.signal;

    const loadAll = async () => {
      try {
        setLoading(true);

        const subjectsRes = await fetch(
          `http://localhost:8080/subjects/my-subjects?classId=${classId}`,
          {
            headers: { Authorization: `Bearer ${token}` },
            signal,
          }
        );

        if (!subjectsRes.ok) {
          setExams([]);
          return;
        }

        const subjectsJson: Subject[] = await subjectsRes.json();
        
        if (!subjectsJson.length) {
          setExams([]);
          return;
        }

        const trimestersRes = await fetch(
          `http://localhost:8080/trimesters`,
          {
            headers: { Authorization: `Bearer ${token}` },
            signal,
          }
        );

        if (!trimestersRes.ok) {
          setExams([]);
          return;
        }

        const trimestersData = await trimestersRes.json();
        const trimestersJson: Trimester[] = trimestersData.content || trimestersData;

        if (!trimestersJson.length) {
          setExams([]);
          return;
        }

        const allExams: Exam[] = [];
        
        await Promise.all(
          subjectsJson.flatMap((subject) =>
            trimestersJson.map(async (trimester) => {
              try {
                const res = await fetch(
                  `http://localhost:8080/exams/by-teacher?classId=${classId}&subjectId=${subject.id}&trimesterId=${trimester.id}`,
                  {
                    headers: { Authorization: `Bearer ${token}` },
                    signal,
                  }
                );

                if (!res.ok) return;

                const data: Exam[] = await res.json();
                if (Array.isArray(data)) {
                  allExams.push(...data);
                }
              } catch (err) {
                if ((err as any).name !== "AbortError") {
                  console.error("Erro ao buscar provas:", err);
                }
              }
            })
          )
        );

        const unique = new Map<number, Exam>();
        allExams.forEach((exam) => unique.set(exam.id, exam));

        const finalList = Array.from(unique.values()).sort((a, b) => {
          const ta = a.trimester?.id ?? 0;
          const tb = b.trimester?.id ?? 0;
          if (ta !== tb) return ta - tb;

          const sa = a.subject?.name ?? "";
          const sb = b.subject?.name ?? "";
          if (sa !== sb) return sa.localeCompare(sb);

          return a.name.localeCompare(b.name);
        });

        setExams(finalList);
      } catch (err) {
        console.error("Erro ao carregar provas:", err);
        setExams([]);
      } finally {
        setLoading(false);
      }
    };

    loadAll();
    return () => {
      controller.abort();
    };
  }, [classId]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!selectedExamId) {
      alert("Selecione uma prova!");
      return;
    }

    const token = localStorage.getItem("token");

    const res = await fetch("http://localhost:8080/warnings", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        exam_id: Number(selectedExamId),
        title,
        content,
      }),
    });

    if (res.ok) {
      alert("Aviso publicado!");
      setTitle("");
      setContent("");
      setSelectedExamId("");
    } else {
      const msg = await res.json();
      alert("Erro ao publicar aviso: " + msg.message);
    }
  };

  return (
    <div className="post-alert">
      <h2 className="post-alert__title">Publicar Aviso</h2>

      <form className="post-alert__form" onSubmit={handleSubmit}>
        <input
          className="post-alert__input"
          placeholder="Título do aviso"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        <select
          className="post-alert__select"
          value={selectedExamId}
          onChange={(e) => setSelectedExamId(e.target.value)}
          disabled={loading}
          required
        >
          <option value="">
            {loading ? "Carregando provas..." : "Selecione uma prova"}
          </option>

          {exams.map((exam) => (
            <option key={exam.id} value={exam.id}>
              {exam.trimester?.name} • {exam.subject?.name} • {exam.name}
            </option>
          ))}
        </select>

        <textarea
          className="post-alert__textarea"
          placeholder="Conteúdo do aviso..."
          value={content}
          onChange={(e) => setContent(e.target.value)}
          required
        />

        <button className="post-alert__button" type="submit">
          Publicar Aviso
        </button>
      </form>
    </div>
  );
}