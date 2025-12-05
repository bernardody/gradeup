import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import TeacherSideBar from "../../Components/SideBar/Teacher-SideBar";
import "./css/SelectSubject.css";

interface Subject {
  id: number;
  name: string;
}

export default function SelectSubject() {
  const { id: classId } = useParams();
  const navigate = useNavigate();

  const [subjects, setSubjects] = useState<Subject[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/subjects/my-subjects?classId=${classId}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => res.json())
      .then(data => {
        setSubjects(data);
        setLoading(false);

        if (data.length === 1) {
          navigate(`/class/${classId}?subject=${data[0].id}`);
        }
      });
  }, [classId, navigate]);

  if (loading) return <div>Carregando...</div>;

  return (
    <div className="class-container">
      <TeacherSideBar />
      <div className="class-content">
        <h2 id="title">Selecione a disciplina</h2>

        <div className="button-group">
          {subjects.map((s) => (
            <button
              key={s.id}
              className="btn-primary"
              onClick={() =>
                navigate(`/class/${classId}?subject=${s.id}`)
              }
            >
              {s.name}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
