import { useEffect, useState } from "react";
import StudentSideBar from "../SideBar/Student-SideBar";
import "../Subjects/css/subjects.css";
import SubjectCard from "../../Components/SubjectCard";

interface Subject {
  id: number;
  name: string;
}

export default function Subjects() {
  const [subjects, setSubjects] = useState<Subject[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
        const token = localStorage.getItem('token');

    fetch("http://localhost:8080/subjects", {
        method: 'GET',
            headers: { 
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
    })
      .then((res) => res.json())
      .then((data) => {
        setSubjects(data.content); 
        setLoading(false);
      })
      .catch((err) => console.error("Erro ao carregar matérias:", err));
  }, []);
  if (loading) {
        return <div>Loading...</div>;
    }
  return (
    <div className="subjects-container">

       <div className="subject-list">
      {subjects.length === 0 && <p>Carregando matérias...</p>}

      {subjects.map((materia) => (
        <SubjectCard key={materia.id} materia={materia} />
      ))}
    </div>

      <div className="sideBar">
        <StudentSideBar />
      </div>
    </div>
  );
}
