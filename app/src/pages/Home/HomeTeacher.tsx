import TeacherSideBar from "../SideBar/Teacher-SideBar";
import HomeBase from "./HomeBase";
import "./css/HomeTeacher.css";

export default function HomeTeacher() {
    return (
    <div className="hometeacher">
        <div className="base">
            <HomeBase />
        </div>
        <div className="sideBar">
            <TeacherSideBar />
        </div>
    </div>
    )
}