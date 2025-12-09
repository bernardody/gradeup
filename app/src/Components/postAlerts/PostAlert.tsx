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
    console.log("🔄 useEffect disparado", { classId, subjectId });
    
    if (!classId || !subjectId) {
      console.log("⚠️ classId ou subjectId ausente");
      setExams([]);
      setLoading(false);
      return;
    }

    const token = localStorage.getItem("token");
    if (!token) {
      console.log("❌ Token não encontrado");
      return;
    }

    const load = async () => {
      try {
        setLoading(true);
        console.log("📡 Buscando trimestres...");
        
        const triRes = await fetch("http://localhost:8080/trimesters", {
          headers: { Authorization: `Bearer ${token}` }
        });

        const triJson = await triRes.json();
        const trimesters: Trimester[] = triJson.content || triJson;
        console.log("✅ Trimestres carregados:", trimesters);

        const allExams: Exam[] = [];

        for (const tri of trimesters) {
          console.log(`📡 Buscando exames para trimestre ${tri.name}...`);
          
          const res = await fetch(
            `http://localhost:8080/exams/by-teacher?classId=${classId}&subjectId=${subjectId}&trimesterId=${tri.id}`,
            { headers: { Authorization: `Bearer ${token}` } }
          );

          if (!res.ok) {
            console.log(`⚠️ Falha ao buscar exames do trimestre ${tri.name}`);
            continue;
          }

          const examsJson = await res.json();
          console.log(`✅ Exames do trimestre ${tri.name}:`, examsJson);
          
          if (Array.isArray(examsJson)) {
            allExams.push(...examsJson);
          }
        }

        console.log("✅ Total de exames carregados:", allExams);
        setExams(allExams);
      } catch (err) {
        console.error("❌ Erro ao carregar exames:", err);
        setExams([]);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [classId, subjectId]);

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();

  console.log("📝 Iniciando envio do alerta...");
  console.log("📋 Dados do formulário:", {
    selectedExamId,
    title,
    content
  });

  if (!selectedExamId) {
    console.log("❌ Nenhum exame selecionado");
    alert("Selecione um exame!");
    return;
  }

  const token = localStorage.getItem("token");
  console.log("🔑 Token:", token ? "Presente" : "Ausente");

  const payload = {
    title,
    content
  };

  console.log("📦 Payload a ser enviado:", payload);
  console.log("📦 Payload JSON:", JSON.stringify(payload));
  console.log("🔗 URL com examId:", `http://localhost:8080/warnings?examId=${selectedExamId}`);

  try {
    const res = await fetch(`http://localhost:8080/warnings?examId=${selectedExamId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(payload)
    });

    console.log("📡 Status da resposta:", res.status);
    console.log("📡 Headers da resposta:", Object.fromEntries(res.headers.entries()));

    const responseText = await res.text();
    console.log("📄 Resposta raw:", responseText);

    if (res.ok) {
      console.log("✅ Aviso publicado com sucesso!");
      alert("Aviso publicado!");
      setTitle("");
      setContent("");
      setSelectedExamId("");
    } else {
      let errorMsg;
      try {
        const msg = JSON.parse(responseText);
        errorMsg = msg.message || msg.error || responseText;
      } catch {
        errorMsg = responseText;
      }
      console.error("❌ Erro do servidor:", errorMsg);
      alert("Erro: " + errorMsg);
    }
  } catch (err) {
    console.error("❌ Erro na requisição:", err);
    alert("Erro ao enviar alerta: " + err);
  }
};

  return (
    <div className="post-alert">
      <h2>Publicar Alerta</h2>

      <form onSubmit={handleSubmit}>
        <select
          value={selectedExamId}
          onChange={(e) => {
            console.log("🎯 Exame selecionado:", e.target.value);
            setSelectedExamId(e.target.value);
          }}
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