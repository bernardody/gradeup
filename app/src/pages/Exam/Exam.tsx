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

interface Subject {
  id: number;
  name: string;
}

export default function Exam() {
  const { classId, id } = useParams();
  const turmaId = classId || id;

  const [trimesters, setTrimester] = useState<Trimester[]>([]);
  const [exams, setExams] = useState<ExamItem[]>([]);
  const [tri, setTri] = useState<number | undefined>(undefined);
  const [selectedExamType, setSelectedExamType] = useState<string>("");
  const [examDate, setExamDate] = useState<string>("");
  const [maxScore, setMaxScore] = useState<string>("");
  const [teacherId, setTeacherId] = useState<number | undefined>(undefined);
  const [subjects, setSubjects] = useState<Subject[]>([]);
  const [selectedSubject, setSelectedSubject] = useState<number | undefined>(undefined);
  const [showSubjectSelection, setShowSubjectSelection] = useState(false);

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

    // Buscar informações do professor
    fetch("http://localhost:8080/users/my-info", { headers })
      .then(res => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
      })
      .then((data) => {
        console.log("Professor logado:", data);
        setTeacherId(data.id);
        
        // Buscar as disciplinas do professor para a turma
        if (turmaId) {
          fetch(`http://localhost:8080/subjects/my-subjects?classId=${turmaId}`, { headers })
            .then(res => {
              if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
              return res.json();
            })
            .then((subjectsData) => {
              console.log("Disciplinas do professor:", subjectsData);
              setSubjects(subjectsData);
              
              // Se tem mais de uma disciplina, mostrar seleção
              if (subjectsData.length > 1) {
                setShowSubjectSelection(true);
              } else if (subjectsData.length === 1) {
                // Se tem apenas uma, selecionar automaticamente
                setSelectedSubject(subjectsData[0].id);
              }
            })
            .catch(err => {
              console.error("Erro ao carregar disciplinas:", err);
            });
        }
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

  const handlePublish = async () => {
    if (showSubjectSelection && !selectedSubject) {
      alert("Por favor, selecione uma disciplina!");
      return;
    }
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
    if (!maxScore || Number(maxScore) <= 0) {
      alert("Por favor, informe a pontuação máxima da prova!");
      return;
    }

    const token = localStorage.getItem("token");
    if (!token) {
      alert("Token não encontrado! Faça login novamente.");
      return;
    }

    const headers = {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    };

    const examData = {
      name: selectedExamType,
      examDate: examDate,
      trimesterId: tri,
      classId: Number(turmaId),
      teacherId: teacherId,
      subjectId: selectedSubject,
      maxScore: Number(maxScore)
    };

    console.log("Publicando prova:", examData);

    try {
      const response = await fetch("http://localhost:8080/exams", {
        method: "POST",
        headers: headers,
        body: JSON.stringify(examData)
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.message || `Erro HTTP: ${response.status}`);
      }

      const result = await response.json();
      console.log("Prova criada:", result);
      
      alert("Prova publicada com sucesso!");
      
      // Limpar campos
      setSelectedExamType("");
      setExamDate("");
      setMaxScore("");
      
      // Recarregar a lista de exames
      fetch("http://localhost:8080/exams?size=1000", { headers })
        .then(res => res.json())
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
          console.error("Erro ao recarregar provas:", err);
        });

    } catch (error) {
      console.error("Erro ao publicar prova:", error);
      alert(`Erro ao publicar prova: ${error instanceof Error ? error.message : 'Erro desconhecido'}`);
    }
  };

  return (
    <div className="exam">
      <div className="exam-content">
        <h2>Minhas provas - Publicar prova</h2>

        {/* Seleção de Disciplina (se necessário) */}
        {showSubjectSelection && (
          <div className="section">
            <h3>Selecione a disciplina:</h3>
            <div className="button-group">
              {subjects.map(subject => (
                <button
                  key={subject.id}
                  onClick={() => setSelectedSubject(subject.id)}
                  className={selectedSubject === subject.id ? "btn-primary active" : "btn-primary"}
                >
                  {subject.name}
                </button>
              ))}
            </div>
          </div>
        )}

        {/* Seleção de Trimestre */}
        {(!showSubjectSelection || selectedSubject) && (
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
        )}

        {/* Seleção de Tipo de Prova */}
        {tri && (!showSubjectSelection || selectedSubject) && (
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
        {selectedExamType && (!showSubjectSelection || selectedSubject) && (
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

            {/* Campo de Pontuação Máxima */}
            <div className="section">
              <h3>Qual a pontuação máxima da prova?</h3>
              <input
                type="number"
                className="date-input"
                placeholder="Ex: 5 ou 10"
                step="0.1"
                min="0"
                value={maxScore}
                onChange={(e) => setMaxScore(e.target.value)}
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