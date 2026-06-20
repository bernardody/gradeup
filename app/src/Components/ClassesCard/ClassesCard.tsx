import './ClassesCard.css';
import { useNavigate } from 'react-router-dom';

interface ClassesCardProps {
  name: string;
  id: string;
}

export default function ClassesCard({ name, id }: ClassesCardProps) {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/class/${id}/subjects`)
  };

  return (
    <button
      onClick={handleClick}
      className="classes-card"
    >
      <span className="classes-card-name">Turma {name}</span>
    </button>
  );
};
