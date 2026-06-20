import './css/Buttons.css';
import { useNavigate } from "react-router-dom";

export default function Turmas() {
    const navigate = useNavigate();
    return (
       <button id="sideBarBtn" onClick={() => navigate("/Classes")}>Turmas</button>
    )
}