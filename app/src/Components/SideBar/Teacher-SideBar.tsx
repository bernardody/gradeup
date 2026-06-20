import InicioProfessor from "./Buttons/InicioProfessor";
import Turmas from "./Buttons/Turmas";
import SideBarBase from "./SideBarBase";
import './css/SideBar.css';

export default function TeacherSideBar() {
    return (
        <div className="sideBar">
            <SideBarBase />
            <div className="buttons">
                <InicioProfessor />
                <Turmas />
            </div>
        </div>
    )
}