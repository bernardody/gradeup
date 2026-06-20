import { Link } from "react-router-dom";
import "./SubjectCard.css";

interface SubjectCardProps {
  materia: {
    id: number;
    name: string;
  };
}

export default function SubjectCard({ materia }: SubjectCardProps) {
  return (
    <Link 
      to={`/subjects/${materia.name}`}
      className="subject-single"
    >
      <p>{materia.name}</p>
    </Link>
  );
}