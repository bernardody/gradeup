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
  const { classId, id } = useParams();
  const turmaId = classId || id;

  const [trimesters, setTrimester] = useState<Trimester[]>([]);
  const [exams, setExams] = useState<ExamItem[]>([]);
  const [tri, setTri] = useState<number | undefined>(undefined);
  const [selectedExamType, setSelectedExamType] = useState<string>("");
  const [examDate, setExamDate] = useState<string>("");
  const [teacherId, setTeacherId] = useState<number | undefined>(undefined);


  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      console.error(" Token não encontrado!");
      return;
    }

    const headers = {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    };
     fetch("http://localhost:8080/users/my-info", { headers })
      .then(res => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
      })
      .then((data) => {
        console.log("Professor logado:", data);
        setTeacherId(data.id);
      })
      .catch(err => {
        console.error("Erro ao carregar dados do professor:", err);
      });
    // Buscar trimestres
    fetch("http://localhost:8080/trimesters", { headers })
      .then(res => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
      })
      .then((data) => {
        setTrimester(data.content);
      })
      .catch(err => {
        console.error("Erro ao carregar trimestres:", err);
      });

    // Buscar exames (com size grande para pegar todos)
    fetch("http://localhost:8080/exams?size=1000", { headers })
      .then(res => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
      })
      .then((data) => {
        if (!turmaId) {
          setExams(data.content);
          return;
        }
        
        const filtered = data.content.filter(
          (e: ExamItem) => e.classEntity.id === Number(turmaId)
        );
        setExams(filtered);
      })
      .catch(err => {
        console.error("Erro ao carregar provas:", err);
      });
  }, [turmaId]);

  // Filtrar exames pelo trimestre selecionado
  const examsOfTrimester = tri 
    ? exams.filter((e) => e.trimester.id === tri)
    : [];

  // Buscar o nome do trimestre selecionado
  const selectedTrimester = trimesters.find(t => t.id === tri);

  // Tipos de prova únicos do trimestre selecionado
  const examTypes = tri 
    ? [...new Set(examsOfTrimester.map(e => e.name))]
    : [];

  const handlePublish = () => {
    if (!tri) {
      alert("Por favor, selecione um trimestre!");
      return;
    }
    if (!selectedExamType) {
      alert("Por favor, selecione o tipo de prova!");
      return;
    }
    if (!examDate) {
      alert("Por favor, selecione a data da prova!");
      return;
    }

    console.log("Publicando prova:", {
      trimestre: tri,
      name: selectedExamType,
      data: examDate,
      classId: turmaId,
      teacherId: teacherId
    });

    // TODO: Fazer POST para o backend aqui
    alert("Prova publicada com sucesso!");
    
    // Limpar campos
    setSelectedExamType("");
    setExamDate("");
  };

  return (
    <div className="exam">
      <div className="exam-content">
        <h2>Minhas provas - Publicar prova</h2>

        {/* Seleção de Trimestre */}
        <div className="section">
          <h3>Selecione o trimestre de sua prova:</h3>
          <div className="button-group">
            {trimesters.map(t => (
              <button
                key={t.id}
                onClick={() => {
                  setTri(t.id);
                  setSelectedExamType(""); // Reseta o tipo ao mudar trimestre
                }}
                className={tri === t.id ? "btn-primary active" : "btn-primary"}
              >
                {t.name}
              </button>
            ))}
          </div>
        </div>

        {/* Seleção de Tipo de Prova */}
        {tri && (
          <div className="section">
            <h3>Selecione qual e a sua prova:</h3>
            <div className="button-group">
              {examTypes.map(type => (
                <button
                  key={type}
                  onClick={() => setSelectedExamType(type)}
                  className={selectedExamType === type ? "btn-secondary active" : "btn-secondary"}
                >
                  {type.toLowerCase()}
                </button>
              ))}
            </div>
          </div>
        )}

        {/* Campo de Conteúdo */}
        {selectedExamType && (
          <>

            {/* Campo de Data */}
            <div className="section">
              <h3>Para quando e essa prova?</h3>
              <input
                type="date"
                className="date-input"
                value={examDate}
                onChange={(e) => setExamDate(e.target.value)}
              />
            </div>

            {/* Botão Publicar */}
            <button className="btn-publish" onClick={handlePublish}>
              Publicar prova
            </button>
          </>
        )}
      </div>

      <div className="sideBar">
        <TeacherSideBar />
      </div>
    </div>
  );
}