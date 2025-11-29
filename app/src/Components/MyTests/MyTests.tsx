import './MyTests.css';
import { useNavigate, useParams } from "react-router-dom";

interface MyTests{
  id: String;
}

export default function MyTests() {
   const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  return (
    <div className="my-tests">
      <h3 className="my-tests__title">Minhas provas</h3>
      <div className="my-tests__buttons">
        <button  className="my-tests__button" onClick={() => navigate(`/class/${id}/exam`)}>
      Publicar Prova
    </button>
        <button className="my-tests__button">
          Ver provas
        </button>
      </div>
    </div>
  );
}
