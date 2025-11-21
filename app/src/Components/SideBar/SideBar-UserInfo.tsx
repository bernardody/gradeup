import './css/SideBar.css';
import { useState, useEffect } from 'react';

interface UserInfoProps {
    studentName?: string;
    teacherName?: string;
    type?: string;
}


export default function UserInfo() {
const [userData, setUserData] = useState<UserInfoProps | null>(null);
const type = localStorage.getItem('type');

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

    return (
        <div className="userInfoContainer">
            <div className="userImg">
                <img></img>
            </div>
            <div className="text">
                <h2 className="userName"><span id="userName">{userData?.studentName || userData?.teacherName}</span></h2>
                <p className="userType"><span id="userType">{type}</span></p>
            </div>
        </div>
    )
}