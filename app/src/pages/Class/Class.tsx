import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import MyTests from "../../Components/MyTests/MyTests";
import MyWorks from "../../Components/MyWorks/MyWorks";
import PostAlert from "../../Components/postAlerts/PostAlert";
import TeacherSideBar from "../../Components/SideBar/Teacher-SideBar";
import "./Class.css";

interface ClassData {
  id: number;
  name: string;
  year: number;
}

export default function Class() {
  const { id } = useParams<{ id: string }>();
  const searchParams = new URLSearchParams(window.location.search);
  const subjectId = searchParams.get("subject"); 

  const [classData, setClassData] = useState<ClassData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [subjectName, setSubjectName] = useState<string>("");

  useEffect(() => {
    const fetchData = async () => {
      if (!id) {
        setError("ID da turma não encontrado");
        setLoading(false);
        return;
      }

      const token = localStorage.getItem("token");
      if (!token) {
        setError("Token não encontrado");
        setLoading(false);
        return;
      }

      try {
        const classRes = await fetch("http://localhost:8080/classes/my-classes", {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });

        if (!classRes.ok) throw new Error("Erro ao buscar turmas");

        const classes = await classRes.json();
        const currentClass = classes.find((c: ClassData) => c.id === Number(id));

        if (!currentClass) {
          setError("Turma não encontrada");
          setLoading(false);
          return;
        }

        setClassData(currentClass);

        if (subjectId) {
          const subjectRes = await fetch(
            `http://localhost:8080/subjects/${subjectId}`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          );

          if (subjectRes.ok) {
            const subjectData = await subjectRes.json();
            setSubjectName(subjectData.name || "");
          } else {
            console.warn("Não foi possível carregar o nome da matéria");
          }
        } else {
          setSubjectName("");
        }
      } catch (err) {
        console.error(err);
        setError("Erro ao carregar dados da turma");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id, subjectId]); 

  if (loading) {
    return (
      <div className="class-container">
        <TeacherSideBar />
        <div className="class-content">
          <div className="loading">Carregando...</div>
        </div>
      </div>
    );
  }

  if (error || !classData || !id) {
    return (
      <div className="class-container">
        <TeacherSideBar />
        <div className="class-content">
          <div className="error">{error || "Turma não encontrada"}</div>
        </div>
      </div>
    );
  }

  const subjectIdNumber = subjectId ? Number(subjectId) : null;

  return (
    <div className="class-container">
      <TeacherSideBar />
      <div className="class-content">
        <header className="class-header">
          <div className="class-header-content">
            <h2 className="class-title">
              Turma {classData.name} ({classData.year})
            </h2>

            {subjectName && (
              <h3 className="selected-subject">
                Matéria selecionada: <span>{subjectName}</span>
              </h3>
            )}
          </div>
        </header>

        <div className="class-grid">
          <MyTests />
          <MyWorks />
        </div>

        <div className="class-alerts">
          <PostAlert classId={Number(id)} subjectId={subjectIdNumber} />
        </div>
      </div>
    </div>
  );
}
