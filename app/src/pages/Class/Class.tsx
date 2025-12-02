import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import MyTests from '../../Components/MyTests/MyTests';
import MyWorks from '../../Components/MyWorks/MyWorks';
import PostAlert from '../..//Components/postAlerts/PostAlert';
import TeacherSideBar from '../../Components/SideBar/Teacher-SideBar';
import './Class.css';

interface ClassData {
  id: number;
  name: string;
  year: number;
}

export default function Class() {
  const { id } = useParams<{ id: string }>();
  const [classData, setClassData] = useState<ClassData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!id) {
      setError('ID da turma não encontrado');
      setLoading(false);
      return;
    }

    const fetchClassData = async () => {
      try {
        const token = localStorage.getItem('token');
        
        if (!token) {
          setError('Token não encontrado');
          setLoading(false);
          return;
        }

        const response = await fetch('http://localhost:8080/classes/my-classes', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        if (!response.ok) {
          throw new Error('Erro ao buscar dados da turma');
        }

        const classes = await response.json();
        const currentClass = classes.find((c: ClassData) => c.id === Number(id));
        
        if (currentClass) {
          setClassData(currentClass);
        } else {
          setError('Turma não encontrada');
        }
        
      } catch (err) {
        setError('Erro ao carregar turma');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchClassData();
  }, [id]);

  if (loading) {
    return (
      <div className="class-container">
        <TeacherSideBar />
        <div className="class-content">
          <div className="loading">Carregando...</div>
        </div>
      </div>
    );
  }

  if (error || !classData || !id) {
    return (
      <div className="class-container">
        <TeacherSideBar />
        <div className="class-content">
          <div className="error">{error || 'Turma não encontrada'}</div>
        </div>
      </div>
    );
  }

  return (
    <div className="class-container">
      <TeacherSideBar />
      <div className="class-content">
        <header className="class-header">
          <div className="class-header-content">
            <h2 className="class-title">
              Turma {classData.name} ({classData.year})
            </h2>
          </div>
        </header>

        <div className="class-grid">
          <MyTests />
          <MyWorks />
        </div>

        <div className="class-alerts">
          <PostAlert classId={Number(id)} />
        </div>
      </div>
    </div>
  );
}