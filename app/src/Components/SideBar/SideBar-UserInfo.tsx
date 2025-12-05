import './css/SideBar.css';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

interface UserInfoProps {
    studentName?: string;
    teacherName?: string;
    type?: string;
}

export default function UserInfo() {
    const [userData, setUserData] = useState<UserInfoProps | null>(null);
    const [menuOpen, setMenuOpen] = useState(false);
    const type = localStorage.getItem('type');
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');

        fetch('http://localhost:8080/dashboard', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
        .then(async response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar dados do estudante');
            }

            return response.json();
        })
        .then((data: UserInfoProps) => {
            console.log("Dashboard data:", data);
            setUserData(data);
        })
        .catch(error => {
            console.error('Erro ao buscar dados do estudante:', error);
        });
    }, []);

    const handleLogout = () => {
        const confirmar = window.confirm("Você quer mesmo sair?");
        if (confirmar) {
            localStorage.clear();
            navigate("/login");
        }
        setMenuOpen(false);
    };

    return (
        <div className="userInfoContainer">
            <div className="userImg">
                <img></img>
            </div>
            <div className="text">
                <h2 className="userName"><span id="userName">{userData?.studentName || userData?.teacherName}</span></h2>
                <p className="userType"><span id="userType">{type}</span></p>
            </div>

            <button 
                className="menuButton" 
                onClick={() => setMenuOpen(!menuOpen)}
                aria-label="Menu"
            >
                <svg 
                    width="20" 
                    height="20" 
                    viewBox="0 0 24 24" 
                    fill="none" 
                    stroke="currentColor" 
                    strokeWidth="2"
                >
                    <circle cx="12" cy="12" r="1" />
                    <circle cx="12" cy="5" r="1" />
                    <circle cx="12" cy="19" r="1" />
                </svg>
            </button>

            {menuOpen && (
                <div 
                    className="menuOverlay" 
                    onClick={() => setMenuOpen(false)}
                ></div>
            )}

            {menuOpen && (
                <div className="menuDropdown">
                    <button className="logoutButton" onClick={handleLogout}>
                        <svg 
                            width="16" 
                            height="16" 
                            viewBox="0 0 24 24" 
                            fill="none" 
                            stroke="currentColor" 
                            strokeWidth="2"
                        >
                            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                            <polyline points="16 17 21 12 16 7" />
                            <line x1="21" y1="12" x2="9" y2="12" />
                        </svg>
                        Sair
                    </button>
                </div>
            )}
        </div>
    )
}