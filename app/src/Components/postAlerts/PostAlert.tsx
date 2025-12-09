import { useEffect, useState } from "react";
import "./PostAlert.css";

interface Trimester {
  id: number;
  name: string;
}

interface Exam {
  id: number;
  name: string;
  trimester: Trimester;
}

export default function PostAlert({
  classId,
  subjectId
}: {
  classId: number;
  subjectId: number | null;
}) {
  const [exams, setExams] = useState<Exam[]>([]);
  const [loading, setLoading] = useState(true);

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [selectedExamId, setSelectedExamId] = useState("");

  useEffect(() => {
    if (!classId || !subjectId) {
      setExams([]);
      setLoading(false);
      return;
    }

    const token = localStorage.getItem("token");
    if (!token) return;

    const load = async () => {
      try {
        setLoading(true);
        const triRes = await fetch("http://localhost:8080/trimesters", {
          headers: { Authorization: `Bearer ${token}` }
        });

        const triJson = await triRes.json();
        const trimesters: Trimester[] = triJson.content || triJson;

        const allExams: Exam[] = [];

        for (const tri of trimesters) {
          const res = await fetch(
            `http://localhost:8080/exams/by-teacher?classId=${classId}&subjectId=${subjectId}&trimesterId=${tri.id}`,
            { headers: { Authorization: `Bearer ${token}` } }
          );

          if (!res.ok) continue;

          const examsJson = await res.json();
          if (Array.isArray(examsJson)) {
            allExams.push(...examsJson);
          }
        }

        setExams(allExams);
      } catch (err) {
        console.error(err);
        setExams([]);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [classId, subjectId]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!selectedExamId) {
      alert("Selecione um exame!");
      return;
    }

    const token = localStorage.getItem("token");

    const res = await fetch("http://localhost:8080/warnings", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({
        exam_id: Number(selectedExamId),
        title,
        content
      })
    });

    if (res.ok) {
      alert("Aviso publicado!");
      setTitle("");
      setContent("");
      setSelectedExamId("");
    } else {
      const msg = await res.json();
      alert("Erro: " + msg.message);
    }
  };

  return (
    <div className="post-alert">
      <h2>Publicar Aviso</h2>

      <form onSubmit={handleSubmit}>
        <select
          value={selectedExamId}
          onChange={(e) => setSelectedExamId(e.target.value)}
          disabled={loading}
          required
        >
          <option value="">
            {loading ? "Carregando exames..." : "Selecione um exame"}
          </option>

          {exams.map((exam) => (
            <option key={exam.id} value={exam.id}>
              {exam.trimester?.name} • {exam.name}
            </option>
          ))}
        </select>

        <input
          placeholder="Título do alerta"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        <textarea
          placeholder="Conteúdo do alerta"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />

        <button type="submit">Enviar alerta</button>
      </form>
    </div>
  );
}
