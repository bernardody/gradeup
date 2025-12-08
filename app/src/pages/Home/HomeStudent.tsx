import StudentSideBar from "../../Components/SideBar/Student-SideBar";
import HomeBase from "../../Components/HomeBase/HomeBase";
import TeacherWarnings from "../../Components/MyAlerts/TeacherWarnings";
import { useNavigate } from 'react-router-dom'; // ← Adicionar
import "./css/HomeStudent.css";
import { useState, useEffect } from "react";
import Graphic from "../../Components/Graphic/Graphic";

interface ClassInfo {
    id?: number;
    name?: string;
    year?: number;
}

interface StudentData {
    studentId: number;
    studentName: string;
    studentEmail: string;
    classInfo?: ClassInfo; 
}

export default function HomeStudent() {
    const [studentData, setStudentData] = useState<StudentData | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const navigate = useNavigate(); // ← Adicionar

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
        .then((data: StudentData) => {
            console.log('Dados recebidos:', data);
            console.log('ClassInfo:', data.classInfo);
            
            if (!data || !data.studentId) {
                throw new Error('Dados do estudante inválidos ou incompletos');
            }
            setStudentData(data);
            setLoading(false);
        })
        .catch(error => {
            console.error('Erro ao buscar dados do estudante:', error);
            setLoading(false);
        });
    }, []);

    // ← Adicionar função de navegação
    const handleViewAllWarnings = () => {
        navigate('/student/avisos');
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!studentData) {
        return <div>Erro ao carregar dados</div>;
    }

    return (
        <div className="homestudent">
            <div className="sideBar">
                <StudentSideBar />
            </div>
            <div className="main-content">
                <div className="base">
                    <HomeBase />
                    <div className="studentInfo">
                        <p>Serie: <span id="serie">{studentData?.classInfo?.year || 'N/A'}</span></p>
                        <p>Turma: <span id="turma">{studentData?.classInfo?.name || 'N/A'}</span></p>
                    </div>
                </div>
                <div className="graphic-container">
                    <div className="graphic">
                        <Graphic />
                    </div>
                </div>
            </div>
            <div className="alertsConteiner">
                <TeacherWarnings 
                    classId={studentData?.classInfo?.id}
                    showViewAllButton={true}
                    onViewAll={handleViewAllWarnings} // ← Adicionar callback
                />
            </div>
        </div>
    );
}