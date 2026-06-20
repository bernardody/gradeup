import './MyWorks.css';
import { useNavigate, useParams } from "react-router-dom";

interface MyWorksProps {
  subjectId: number | null;
}

export default function MyWorks({ subjectId }: MyWorksProps) {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  return (
    <div className="my-works">
      <h3 className="my-works__title">Meus Avisos</h3>
      <div className="my-works__buttons">
        <button 
          className="my-works__button"
          onClick={() => {
            if (!subjectId) {
              alert("Você deve selecionar uma matéria antes.");
              return;
            }
            navigate(`/class/${id}/warnings?subject=${subjectId}`);
          }}
        >
          Ver Avisos
        </button>
      </div>
    </div>
  );
}