import './css/SideBar.css';

export default function UserInfo() {
    return (
        <div className="userInfoContainer">
            <div className="userImg">
                <img></img>
            </div>
            <div className="text">
                <h2 className="userName">Nome do user</h2>
                <p className="userType">tipo</p>
            </div>
        </div>
    )
}