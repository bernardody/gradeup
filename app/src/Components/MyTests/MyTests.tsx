import './MyTests.css';
import { useNavigate, useParams } from "react-router-dom";

interface MyTestsProps {
  subjectId: number | null;
}

export default function MyTests({ subjectId }: MyTestsProps) {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  return (
    <div className="my-tests">
      <h3 className="my-tests__title">Minhas provas</h3>

      <div className="my-tests__buttons">

        <button
          className="my-tests__button"
          onClick={() => {
            if (!subjectId) {
              alert("Você deve selecionar uma matéria antes.");
              return;
            }
            navigate(`/class/${id}/exam?subject=${subjectId}`);
          }}
        >
          Publicar Prova
        </button>

        <button
          className="my-tests__button"
          onClick={() =>
            navigate(`/class/${id}/exam-list?subject=${subjectId}`)
          }
        >
          Ver provas
        </button>

      </div>
    </div>
  );
}
