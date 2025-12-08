import React, { useState, useEffect } from 'react';
import StudentSideBar from "../../Components/SideBar/Student-SideBar";
import './AllStudentWarnings.css';

interface Teacher {
  id: number;
  name: string;
  email: string;
  type: string;
}

interface Subject {
  id: number;
  name: string;
}

interface Trimester {
  id: number;
  name: string;
  maxPoints: number;
  minPoints: number;
}

interface ClassEntity {
  id: number;
  name: string;
  year: number;
}

interface ExamEntity {
  id: number;
  classEntity: ClassEntity;
  subject: Subject;
  teacher: Teacher;
  trimester: Trimester;
  name: string;
  maxScore: number;
  examDate: string;
}

interface Warning {
  id: number;
  examEntity: ExamEntity;
  title: string;
  content: string;
  createdAt: string;
}

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

export default function AllStudentWarnings() {
  const [warnings, setWarnings] = useState<Warning[]>([]);
  const [studentData, setStudentData] = useState<StudentData | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const token = localStorage.getItem('token');

      // Buscar dados do estudante
      const dashboardResponse = await fetch('http://localhost:8080/dashboard', {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (!dashboardResponse.ok) {
        throw new Error('Erro ao buscar dados do estudante');
      }

      const studentData: StudentData = await dashboardResponse.json();
      setStudentData(studentData);

      // Buscar avisos
      const warningsResponse = await fetch(
        `http://localhost:8080/warnings/by-student?classId=${studentData.classInfo?.id}`,
        {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        }
      );

      if (!warningsResponse.ok) {
        throw new Error('Erro ao carregar avisos');
      }

      const warningsData: Warning[] = await warningsResponse.json();
      const sortedWarnings = warningsData.sort((a, b) =>
        new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
      );
      setWarnings(sortedWarnings);
      setLoading(false);
    } catch (err) {
      setError('Erro ao carregar os dados');
      setLoading(false);
      console.error(err);
    }
  };

  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  };

  const formatDateTime = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  if (loading) {
    return (
      <div className="all-warnings-page">
        <div className="sideBar">
          <StudentSideBar />
        </div>
        <div className="all-warnings-content">
          <div className="loading-container">
            <div className="spinner"></div>
            <p>Carregando avisos...</p>
          </div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="all-warnings-page">
        <div className="sideBar">
          <StudentSideBar />
        </div>
        <div className="all-warnings-content">
          <div className="error-container">
            <p>❌ {error}</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="all-warnings-page">
      <div className="sideBar">
        <StudentSideBar />
      </div>
      <div className="all-warnings-content">
        <div className="all-warnings-header">
          <h1>📢 Todos os Avisos dos Professores</h1>
          {studentData && (
            <div className="student-info-header">
              <span>Turma: {studentData.classInfo?.name}</span>
              <span>Total de avisos: {warnings.length}</span>
            </div>
          )}
        </div>

        {warnings.length === 0 ? (
          <div className="empty-warnings">
            <p>Nenhum aviso disponível</p>
          </div>
        ) : (
          <div className="warnings-grid">
            {warnings.map((warning) => (
              <div key={warning.id} className="warning-card-full">
                <div className="warning-header-full">
                  <h3 className="warning-title-full">{warning.title}</h3>
                  <span className="warning-subject-full">
                    {warning.examEntity.subject.name}
                  </span>
                </div>

                <div className="warning-content-full">
                  <p>{warning.content}</p>
                </div>

                <div className="warning-details-full">
                  <div className="detail-row">
                    <div className="detail-item-full">
                      <span className="detail-label-full">👤 Professor:</span>
                      <span className="detail-value-full">
                        {warning.examEntity.teacher.name}
                      </span>
                    </div>

                    <div className="detail-item-full">
                      <span className="detail-label-full">📝 Prova:</span>
                      <span className="detail-value-full">
                        {warning.examEntity.name}
                      </span>
                    </div>
                  </div>

                  <div className="detail-row">
                    <div className="detail-item-full">
                      <span className="detail-label-full">📅 Data da Prova:</span>
                      <span className="detail-value-full">
                        {formatDate(warning.examEntity.examDate)}
                      </span>
                    </div>

                    <div className="detail-item-full">
                      <span className="detail-label-full">📊 Pontuação:</span>
                      <span className="detail-value-full">
                        {warning.examEntity.maxScore} pontos
                      </span>
                    </div>
                  </div>
                </div>

                <div className="warning-footer-full">
                  <span className="trimester-badge">
                    {warning.examEntity.trimester.name}
                  </span>
                  <span className="publish-date">
                    📅 Publicado: {formatDateTime(warning.createdAt)}
                  </span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}