import './css/Buttons.css';
import { useNavigate } from "react-router-dom";

export default function Disciplinas() {
  const navigate = useNavigate();
  return (
    <button id="sideBarBtn" onClick={() => navigate("/Subjects")}>
      Disciplinas
    </button>
  );
}