import TeacherSideBar from "../SideBar/Teacher-SideBar";
import HomeBase from "./HomeBase";
import "./css/HomeTeacher.css";
import "./css/HomeBase.css";
import Graphic from "../Home/Graphic"

export default function HomeTeacher() {
    return (
    <div className="hometeacher">
        <div className="sideBar">
            <TeacherSideBar />
        </div>
        <div className="base">
            <HomeBase />
            <div className="graphic-container">
                <div id="title">
                    <h1>Visao geral das notas de suas turmas</h1>
                </div>
                <div className="graphic">
                    <Graphic />
                </div>
            </div>
        </div>
    </div>
    )
}