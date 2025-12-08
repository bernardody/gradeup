import { useState, useEffect } from 'react';
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
  seen?: boolean;
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

// Ícones SVG
const FilterIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <polygon points="22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3"></polygon>
  </svg>
);

const CheckIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <polyline points="20 6 9 17 4 12"></polyline>
  </svg>
);

const EyeIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
    <circle cx="12" cy="12" r="3"></circle>
  </svg>
);

export default function AllStudentWarnings() {
  const [warnings, setWarnings] = useState<Warning[]>([]);
  const [filteredWarnings, setFilteredWarnings] = useState<Warning[]>([]);
  const [studentData, setStudentData] = useState<StudentData | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  
  // Estados dos filtros
  const [showFilters, setShowFilters] = useState(false);
  const [selectedSubject, setSelectedSubject] = useState<string>('all');
  const [selectedTeacher, setSelectedTeacher] = useState<string>('all');
  const [selectedDateRange, setSelectedDateRange] = useState<string>('all');
  const [selectedSeenStatus, setSelectedSeenStatus] = useState<string>('all');

  // Estados para listas únicas
  const [subjects, setSubjects] = useState<string[]>([]);
  const [teachers, setTeachers] = useState<string[]>([]);

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    applyFilters();
  }, [warnings, selectedSubject, selectedTeacher, selectedDateRange, selectedSeenStatus]);

  const fetchData = async () => {
    try {
      setLoading(true);
      const token = localStorage.getItem('token');

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
      
      // Carrega o estado de "visto" do localStorage
      const seenWarnings = JSON.parse(localStorage.getItem('seenWarnings') || '[]');
      const warningsWithSeen = warningsData.map(w => ({
        ...w,
        seen: seenWarnings.includes(w.id)
      }));
      
      const sortedWarnings = warningsWithSeen.sort((a, b) =>
        new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
      );
      
      setWarnings(sortedWarnings);
      
      // Extrai subjects e teachers únicos
      const uniqueSubjects = [...new Set(warningsData.map(w => w.examEntity.subject.name))];
      const uniqueTeachers = [...new Set(warningsData.map(w => w.examEntity.teacher.name))];
      
      setSubjects(uniqueSubjects);
      setTeachers(uniqueTeachers);
      
      setLoading(false);
    } catch (err) {
      setError('Erro ao carregar os dados');
      setLoading(false);
      console.error(err);
    }
  };

  const toggleSeenStatus = (warningId: number) => {
    const updatedWarnings = warnings.map(w => 
      w.id === warningId ? { ...w, seen: !w.seen } : w
    );
    setWarnings(updatedWarnings);
    
    // Atualiza localStorage
    const seenWarnings = updatedWarnings.filter(w => w.seen).map(w => w.id);
    localStorage.setItem('seenWarnings', JSON.stringify(seenWarnings));
  };

  const applyFilters = () => {
    let filtered = [...warnings];

    // Filtro por matéria
    if (selectedSubject !== 'all') {
      filtered = filtered.filter(w => w.examEntity.subject.name === selectedSubject);
    }

    // Filtro por professor
    if (selectedTeacher !== 'all') {
      filtered = filtered.filter(w => w.examEntity.teacher.name === selectedTeacher);
    }

    // Filtro por data
    if (selectedDateRange !== 'all') {
      const now = new Date();
      const dayInMs = 24 * 60 * 60 * 1000;
      
      filtered = filtered.filter(w => {
        const warningDate = new Date(w.createdAt);
        const diffDays = Math.floor((now.getTime() - warningDate.getTime()) / dayInMs);
        
        switch (selectedDateRange) {
          case 'today':
            return diffDays === 0;
          case 'week':
            return diffDays <= 7;
          case 'month':
            return diffDays <= 30;
          default:
            return true;
        }
      });
    }

    // Filtro por status de visto
    if (selectedSeenStatus === 'seen') {
      filtered = filtered.filter(w => w.seen);
    } else if (selectedSeenStatus === 'unseen') {
      filtered = filtered.filter(w => !w.seen);
    }

    setFilteredWarnings(filtered);
  };

  const clearFilters = () => {
    setSelectedSubject('all');
    setSelectedTeacher('all');
    setSelectedDateRange('all');
    setSelectedSeenStatus('all');
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
            <p>{error}</p>
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
        {/* Header com botão de filtros */}
        <div className="all-warnings-header">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div>
              <h1>Todos os seus avisos</h1>
              {studentData && (
                <div className="student-info-header">
                  <span>Turma: {studentData.classInfo?.name}</span>
                  <span>Total de avisos: {warnings.length}</span>
                  <span>Filtrados: {filteredWarnings.length}</span>
                </div>
              )}
            </div>
            <button
              onClick={() => setShowFilters(!showFilters)}
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: '0.5rem',
                padding: '0.75rem 1.5rem',
                backgroundColor: '#667eea',
                color: 'white',
                border: 'none',
                borderRadius: '8px',
                cursor: 'pointer',
                fontSize: '0.95rem',
                fontWeight: '600',
                transition: 'background-color 0.2s'
              }}
              onMouseOver={(e) => e.currentTarget.style.backgroundColor = '#5568d3'}
              onMouseOut={(e) => e.currentTarget.style.backgroundColor = '#667eea'}
            >
              <FilterIcon />
              {showFilters ? 'Ocultar Filtros' : 'Mostrar Filtros'}
            </button>
          </div>
        </div>

        {/* Painel de Filtros */}
        {showFilters && (
          <div style={{
            background: 'white',
            borderRadius: '12px',
            padding: '1.5rem',
            marginBottom: '2rem',
            boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)'
          }}>
            <div style={{
              display: 'grid',
              gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
              gap: '1rem',
              marginBottom: '1rem'
            }}>
              {/* Filtro Matéria */}
              <div>
                <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', fontWeight: '600', color: '#2c3e50' }}>
                  Matéria
                </label>
                <select
                  value={selectedSubject}
                  onChange={(e) => setSelectedSubject(e.target.value)}
                  style={{
                    width: '100%',
                    padding: '0.6rem',
                    border: '1px solid #ddd',
                    borderRadius: '6px',
                    fontSize: '0.95rem'
                  }}
                >
                  <option value="all">Todas as matérias</option>
                  {subjects.map(subject => (
                    <option key={subject} value={subject}>{subject}</option>
                  ))}
                </select>
              </div>

              {/* Filtro Professor */}
              <div>
                <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', fontWeight: '600', color: '#2c3e50' }}>
                  Professor
                </label>
                <select
                  value={selectedTeacher}
                  onChange={(e) => setSelectedTeacher(e.target.value)}
                  style={{
                    width: '100%',
                    padding: '0.6rem',
                    border: '1px solid #ddd',
                    borderRadius: '6px',
                    fontSize: '0.95rem'
                  }}
                >
                  <option value="all">Todos os professores</option>
                  {teachers.map(teacher => (
                    <option key={teacher} value={teacher}>{teacher}</option>
                  ))}
                </select>
              </div>

              {/* Filtro Data */}
              <div>
                <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', fontWeight: '600', color: '#2c3e50' }}>
                  Período
                </label>
                <select
                  value={selectedDateRange}
                  onChange={(e) => setSelectedDateRange(e.target.value)}
                  style={{
                    width: '100%',
                    padding: '0.6rem',
                    border: '1px solid #ddd',
                    borderRadius: '6px',
                    fontSize: '0.95rem'
                  }}
                >
                  <option value="all">Todos os períodos</option>
                  <option value="today">Hoje</option>
                  <option value="week">Última semana</option>
                  <option value="month">Último mês</option>
                </select>
              </div>

              {/* Filtro Status Visto */}
              <div>
                <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', fontWeight: '600', color: '#2c3e50' }}>
                  Status
                </label>
                <select
                  value={selectedSeenStatus}
                  onChange={(e) => setSelectedSeenStatus(e.target.value)}
                  style={{
                    width: '100%',
                    padding: '0.6rem',
                    border: '1px solid #ddd',
                    borderRadius: '6px',
                    fontSize: '0.95rem'
                  }}
                >
                  <option value="all">Todos</option>
                  <option value="unseen">Não vistos</option>
                  <option value="seen">Vistos</option>
                </select>
              </div>
            </div>

            {/* Botão Limpar Filtros */}
            <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
              <button
                onClick={clearFilters}
                style={{
                  padding: '0.6rem 1.2rem',
                  backgroundColor: '#ecf0f1',
                  color: '#2c3e50',
                  border: 'none',
                  borderRadius: '6px',
                  cursor: 'pointer',
                  fontSize: '0.9rem',
                  fontWeight: '600',
                  transition: 'background-color 0.2s'
                }}
                onMouseOver={(e) => e.currentTarget.style.backgroundColor = '#d5dbdb'}
                onMouseOut={(e) => e.currentTarget.style.backgroundColor = '#ecf0f1'}
              >
                Limpar Filtros
              </button>
            </div>
          </div>
        )}

        {/* Lista de Avisos */}
        {filteredWarnings.length === 0 ? (
          <div className="empty-warnings">
            <p>Nenhum aviso encontrado com os filtros aplicados</p>
          </div>
        ) : (
          <div className="warnings-grid">
            {filteredWarnings.map((warning) => (
              <div
                key={warning.id}
                className="warning-card-full"
                style={{ opacity: warning.seen ? 0.7 : 1 }}
              >
                <div className="warning-header-full">
                  <h3 className="warning-title-full">{warning.title}</h3>
                  <button
                    onClick={() => toggleSeenStatus(warning.id)}
                    style={{
                      background: warning.seen ? 'rgba(46, 204, 113, 0.2)' : 'rgba(255, 255, 255, 0.2)',
                      border: 'none',
                      borderRadius: '6px',
                      padding: '0.5rem',
                      cursor: 'pointer',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      transition: 'background-color 0.2s'
                    }}
                    title={warning.seen ? 'Marcar como não visto' : 'Marcar como visto'}
                  >
                    {warning.seen ? <CheckIcon /> : <EyeIcon />}
                  </button>
                </div>

                <div style={{ display: 'flex', padding: '0 1.25rem', paddingTop: '0.5rem' }}>
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
                      <span className="detail-label-full">Professor:</span>
                      <span className="detail-value-full">
                        {warning.examEntity.teacher.name}
                      </span>
                    </div>

                    <div className="detail-item-full">
                      <span className="detail-label-full">Prova:</span>
                      <span className="detail-value-full">
                        {warning.examEntity.name}
                      </span>
                    </div>
                  </div>

                  <div className="detail-row">
                    <div className="detail-item-full">
                      <span className="detail-label-full">Data da Prova:</span>
                      <span className="detail-value-full">
                        {formatDate(warning.examEntity.examDate)}
                      </span>
                    </div>

                    <div className="detail-item-full">
                      <span className="detail-label-full">Pontuação:</span>
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
                    Publicado: {formatDateTime(warning.createdAt)}
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