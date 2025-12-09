import React, { useState, useEffect } from 'react';
import { useParams, useSearchParams, useNavigate } from 'react-router-dom';
import './ExamList.css';
import TeacherSideBar from '../../Components/SideBar/Teacher-SideBar';

interface ClassEntity {
  id: number;
  name: string;
  year: number;
}

interface Subject {
  id: number;
  name: string;
}

interface Teacher {
  id: number;
  name: string;
  email: string;
  type: string;
}

interface Trimester {
  id: number;
  name: string;
  maxPoints: number;
  minPoints: number;
}

interface Exam {
  id: number;
  classEntity: ClassEntity;
  subject: Subject;
  teacher: Teacher;
  trimester: Trimester;
  name: string;
  maxScore: number;
  examDate: string;
}

const ExamList: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const subjectId = searchParams.get('subject');

  const [exams, setExams] = useState<Exam[]>([]);
  const [trimesters, setTrimesters] = useState<Trimester[]>([]);
  const [selectedTrimester, setSelectedTrimester] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

 
  useEffect(() => {
    fetchTrimesters();
    
  }, []);


  useEffect(() => {
    if (selectedTrimester && id && subjectId) {
      fetchExams();
    }
   
  }, [selectedTrimester]);

  const fetchTrimesters = async () => {
    try {
      const token = localStorage.getItem('token');
      
      if (!token) {
        throw new Error('Token não encontrado. Faça login novamente.');
      }

      const response = await fetch('http://localhost:8080/trimesters', {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('Erro ao buscar trimestres');
      }

      const data: any = await response.json();
      
      
      const trimestersArray = data.content || data;
      
      setTrimesters(trimestersArray);
      
      
      if (trimestersArray.length > 0) {
        setSelectedTrimester(trimestersArray[0].id);
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro ao buscar trimestres');
      setLoading(false);
    }
  };

  const fetchExams = async () => {
    try {
      setLoading(true);
      setError(null);

      const token = localStorage.getItem('token');
      
      if (!token) {
        throw new Error('Token não encontrado. Faça login novamente.');
      }

      const url = `http://localhost:8080/exams/by-teacher?classId=${id}&subjectId=${subjectId}&trimesterId=${selectedTrimester}`;
      
      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        if (response.status === 401) {
          throw new Error('Não autorizado. Faça login novamente.');
        }
        throw new Error('Erro ao buscar provas');
      }

      const data: Exam[] = await response.json();
      setExams(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro desconhecido');
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
  };

  const handleManageGrades = (exam: Exam) => {
    navigate(`/grades/manage/${exam.id}`, {
      state: { exam }
    });
  };

  if (loading) {
    return <div className="exam-list__loading">Carregando provas...</div>;
  }

  if (error) {
    return <div className="exam-list__error">Erro: {error}</div>;
  }

  return (
    <div className="exam-list">
      <div className="exam-list__header">
        <h1>Provas</h1>
        {exams.length > 0 && (
          <div className="exam-list__info">
            <p className="exam-list__class">Turma: {exams[0].classEntity.name}</p>
            <p className="exam-list__subject">Disciplina: {exams[0].subject.name}</p>
          </div>
        )}
      </div>

      <div className="exam-list__filters">
        <label htmlFor="trimester-select">Trimestre:</label>
        <select
          id="trimester-select"
          value={selectedTrimester || ''}
          onChange={(e) => setSelectedTrimester(Number(e.target.value))}
          className="exam-list__select"
        >
          {trimesters.map(trimester => (
            <option key={trimester.id} value={trimester.id}>
              {trimester.name}
            </option>
          ))}
        </select>
      </div>

      {exams.length === 0 ? (
        <div className="exam-list__empty">
          Nenhuma prova encontrada para este trimestre.
        </div>
      ) : (
        <div className="exam-list__grid">
          {exams.map(exam => (
            <div key={exam.id} className="exam-card">
              <div className="exam-card__header">
                <h3 className="exam-card__name">{exam.name}</h3>
              </div>
              
              <div className="exam-card__body">
                <div className="exam-card__info">
                  <span className="exam-card__label">Data:</span>
                  <span className="exam-card__value">{formatDate(exam.examDate)}</span>
                </div>
                
                <div className="exam-card__info">
                  <span className="exam-card__label">Nota máxima:</span>
                  <span className="exam-card__value">{exam.maxScore.toFixed(2)}</span>
                </div>
                
                <div className="exam-card__info">
                  <span className="exam-card__label">Trimestre:</span>
                  <span className="exam-card__value">{exam.trimester.name}</span>
                </div>
                
                <div className="exam-card__info">
                  <span className="exam-card__label">Professor(a):</span>
                  <span className="exam-card__value">{exam.teacher.name}</span>
                </div>
              </div>

              <div className="exam-card__footer">
                <button 
                  className="exam-card__button exam-card__button--grades"
                  onClick={() => handleManageGrades(exam)}
                >
                  Gerenciar notas
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
      <div className="sideBar">
          <TeacherSideBar />
        </div> 
    </div>
  );
};


export default ExamList;