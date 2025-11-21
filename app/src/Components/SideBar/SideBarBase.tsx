import './css/SideBar.css';
import UserInfo from './SideBar-UserInfo';

export default function SideBarBase() {
    return (
        <div className="sideBarBase">
            <h1 className="sideBarTitle">Grade Up</h1>
            <UserInfo />
        </div>
    )
}