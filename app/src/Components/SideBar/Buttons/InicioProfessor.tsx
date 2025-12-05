import './css/Buttons.css';
import { useNavigate } from "react-router-dom";


export default function Inicio() {
  const navigate = useNavigate();
  return (
    <button id="sideBarBtn" onClick={() => navigate("/HomeTeacher")}>
      Início
    </button>
  );
}