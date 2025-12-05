import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import TeacherSideBar from "../../Components/SideBar/Teacher-SideBar";
import "./Exam.css";

interface Trimester {
  id: number;
  name: string;
}

interface ExamItem {
  id: number;
  name: string;
  trimester: { id: number; name: string };
  classEntity: { id: number; name: string; year: number };
}

export default function Exam() {
  const { id } = useParams();
  const turmaId = id;
  const [trimesters, setTrimesters] = useState<Trimester[]>([]);
  const [exams, setExams] = useState<ExamItem[]>([]);
  const [tri, setTri] = useState<number | undefined>(undefined);
  const [selectedExamType, setSelectedExamType] = useState<string>("");
  const [examDate, setExamDate] = useState<string>("");
  const [maxScore, setMaxScore] = useState<string>("");
  const [teacherId, setTeacherId] = useState<number | undefined>(undefined);
  const searchParams = new URLSearchParams(window.location.search);
  const subjectId = searchParams.get("subject");

  const materiaNaoSelecionada = !subjectId;

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) return;

    const headers = {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    };

    fetch("http://localhost:8080/users/my-info", { headers })
      .then((res) => res.json())
      .then((data) => setTeacherId(data.id))
      .catch(() => console.error("Erro ao buscar professor"));

    fetch("http://localhost:8080/trimesters", { headers })
      .then((res) => res.json())
      .then((data) => setTrimesters(data.content))
      .catch(() => console.error("Erro ao buscar trimestres"));

    fetch("http://localhost:8080/exams?size=1000", { headers })
      .then((res) => res.json())
      .then((data) => {
        const filtered = data.content.filter(
          (e: ExamItem) => e.classEntity.id === Number(turmaId)
        );
        setExams(filtered);
      })
      .catch(() => console.error("Erro ao buscar provas"));
  }, [turmaId]);

  const examsOfTrimester = tri
    ? exams.filter((e) => e.trimester.id === tri)
    : [];

  const examTypes = tri
    ? [...new Set(examsOfTrimester.map((e) => e.name))]
    : [];

  const handlePublish = async () => {
    if (materiaNaoSelecionada) {
      alert("Selecione uma matéria antes de publicar a prova!");
      return;
    }
    if (!tri) {
      alert("Selecione o trimestre!");
      return;
    }
    if (!selectedExamType) {
      alert("Selecione o tipo da prova!");
      return;
    }
    if (!examDate) {
      alert("Selecione a data!");
      return;
    }
    if (!maxScore) {
      alert("Digite a pontuação máxima!");
      return;
    }

    const token = localStorage.getItem("token");
    const headers = {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    };

    const examData = {
      name: selectedExamType,
      examDate,
      trimesterId: tri,
      classId: Number(turmaId),
      teacherId,
      subjectId: Number(subjectId),
      maxScore: Number(maxScore),
    };

    try {
      const response = await fetch("http://localhost:8080/exams", {
        method: "POST",
        headers,
        body: JSON.stringify(examData),
      });

      if (!response.ok) throw new Error("Erro ao publicar");

      alert("Prova publicada!");
      setSelectedExamType("");
      setExamDate("");
      setMaxScore("");
    } catch {
      alert("Erro ao publicar prova");
    }
  };

  return (
    <div className="exam">
      <div className="exam-content">
        <h2>Publicar Prova</h2>

        {materiaNaoSelecionada ? (
          <p style={{ color: "red", fontSize: "18px" }}>
            Você deve escolher uma matéria antes de publicar uma prova.
          </p>
        ) : (
          <>
            <div className="section">
              <h3>Selecione o trimestre</h3>
              <div className="button-group">
                {trimesters.map((t) => (
                  <button
                    key={t.id}
                    onClick={() => {
                      setTri(t.id);
                      setSelectedExamType("");
                    }}
                    className={tri === t.id ? "btn-primary active" : "btn-primary"}
                  >
                    {t.name}
                  </button>
                ))}
              </div>
            </div>

            {tri && (
              <div className="section">
                <h3>Tipo da prova</h3>
                <div className="button-group">
                  {examTypes.map((type) => (
                    <button
                      key={type}
                      onClick={() => setSelectedExamType(type)}
                      className={
                        selectedExamType === type
                          ? "btn-secondary active"
                          : "btn-secondary"
                      }
                    >
                      {type.toLowerCase()}
                    </button>
                  ))}
                </div>
              </div>
            )}

            {selectedExamType && (
              <>
                <div className="section">
                  <h3>Data da prova</h3>
                  <input
                    type="date"
                    className="date-input"
                    value={examDate}
                    onChange={(e) => setExamDate(e.target.value)}
                  />
                </div>

                <div className="section">
                  <h3>Pontuação máxima</h3>
                  <input
                    type="number"
                    className="date-input"
                    placeholder="Ex: 10"
                    min="1"
                    step="0.1"
                    value={maxScore}
                    onChange={(e) => setMaxScore(e.target.value)}
                  />
                </div>

                <button className="btn-publish" onClick={handlePublish}>
                  Publicar prova
                </button>
              </>
            )}
          </>
        )}
      </div>

      <div className="sideBar">
        <TeacherSideBar />
      </div>
    </div>
  );
}
