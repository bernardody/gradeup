import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './AllTeacherWarnings.css';
import TeacherSideBar from '../../Components/SideBar/Teacher-SideBar';

interface Trimester {
  id: number;
  name: string;
  maxPoints: number;
  minPoints: number;
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

export default function WarningsList() {
  const { id } = useParams<{ id: string }>();
  const searchParams = new URLSearchParams(window.location.search);
  const subjectId = searchParams.get("subject");
  
  const [warnings, setWarnings] = useState<Warning[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [subjectName, setSubjectName] = useState<string>("");

  useEffect(() => {
    const fetchWarnings = async () => {
      if (!id) {
        setError('ID da turma não encontrado');
        setLoading(false);
        return;
      }

      const token = localStorage.getItem('token');
      if (!token) {
        setError('Token não encontrado');
        setLoading(false);
        return;
      }

      try {
        // Buscar avisos
        const response = await fetch(
          `http://localhost:8080/warnings/by-teacher?classId=${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          }
        );

        if (!response.ok) {
          throw new Error('Erro ao buscar avisos');
        }

        const data = await response.json();
        
        // Filtrar por matéria se subjectId estiver presente
        let filteredWarnings = data;
        if (subjectId) {
          filteredWarnings = data.filter(
            (warning: Warning) => warning.examEntity.subject.id === Number(subjectId)
          );
        }
        
        setWarnings(filteredWarnings);

        // Buscar nome da matéria se subjectId estiver presente
        if (subjectId) {
          const subjectRes = await fetch(
            `http://localhost:8080/subjects/${subjectId}`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          );

          if (subjectRes.ok) {
            const subjectData = await subjectRes.json();
            setSubjectName(subjectData.name || "");
          }
        }
      } catch (err) {
        console.error(err);
        setError('Erro ao carregar avisos');
      } finally {
        setLoading(false);
      }
    };

    fetchWarnings();
  }, [id, subjectId]);

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  };

  if (loading) {
    return (
      <div className="warnings-list">
        <div className="warnings-list__loading">Carregando avisos...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="warnings-list">
        <div className="warnings-list__error">{error}</div>
      </div>
    );
  }

  return (
    <div className="warnings-list">
      <h2 className="warnings-list__title">
        Meus Avisos
        {subjectName && <span className="warnings-list__subject"> - {subjectName}</span>}
      </h2>
      
      {warnings.length === 0 ? (
        <div className="warnings-list__empty">
          {subjectId 
            ? `Nenhum aviso publicado para a matéria ${subjectName}.`
            : 'Nenhum aviso publicado ainda.'
          }
        </div>
      ) : (
        <div className="warnings-list__container">
          {warnings.map((warning) => (
            <div key={warning.id} className="warning-card">
              <div className="warning-card__header">
                <h3 className="warning-card__title">{warning.title}</h3>
                <span className="warning-card__date">
                  {formatDate(warning.createdAt)}
                </span>
              </div>

              <div className="warning-card__exam-info">
                <div className="warning-card__info-item">
                  <strong>Prova:</strong> {warning.examEntity.name}
                </div>
                <div className="warning-card__info-item">
                  <strong>Matéria:</strong> {warning.examEntity.subject.name}
                </div>
                <div className="warning-card__info-item">
                  <strong>Trimestre:</strong> {warning.examEntity.trimester.name}
                </div>
                <div className="warning-card__info-item">
                  <strong>Data da Prova:</strong>{' '}
                  {formatDate(warning.examEntity.examDate)}
                </div>
                <div className="warning-card__info-item">
                  <strong>Pontuação:</strong> {warning.examEntity.maxScore.toFixed(2)}
                </div>
              </div>

              <div className="warning-card__content">
                <strong>Conteúdo:</strong>
                <p>{warning.content}</p>
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
}