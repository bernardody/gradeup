import StudentSideBar from "../SideBar/Student-SideBar";
import Alerts from "./Alerts";
import HomeBase from "./HomeBase";

export default function HomeStudent() {
    return (
    <div className="homestudent">
        <div className="base">
            <HomeBase />
        </div>
        <div className="alerts">
            <Alerts />
        </div>
        <div className="sideBar">
            <StudentSideBar />
        </div>
    </div>
    )
}