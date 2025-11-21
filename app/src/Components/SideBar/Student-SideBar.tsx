import Disciplinas from "./Buttons/Disciplinas";
import Inicio from "./Buttons/Inicio";
import SideBarBase from "./SideBarBase";
import './css/SideBar.css';

export default function StudentSideBar() {
    return (
        <div className="sideBar">
            <SideBarBase />
            <div className="buttons">
                <Inicio />
                <Disciplinas />
            </div>
        </div>
    )
}