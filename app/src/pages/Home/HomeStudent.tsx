import StudentSideBar from "../SideBar/Student-SideBar";
import Alerts from "./Alerts";
import HomeBase from "./HomeBase";
import "./css/HomeStudent.css";

export default function HomeStudent() {
    return (
    <div className="homestudent">
        <div className="base">
            <HomeBase />
            <div className="studentInfo">
                <p>Serie: <span id="serie">2ano</span></p>
                <p>Turma: <span id="turma">211</span></p>
            </div>
        </div>
        <div className="alertsConteiner">
            <p id="alertsTitle">Avisos da semana</p>
            <div className="alerts">
            <Alerts />
            </div>
        </div>
        <div className="sideBar">
            <StudentSideBar />
        </div>
    </div>
    )
}