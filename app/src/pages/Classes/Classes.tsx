import { useEffect, useState } from 'react';
import ClassesCard from '../../Components/ClassesCard/ClassesCard';
import './Classes.css';
import TeacherSideBar from '../../Components/SideBar/Teacher-SideBar';

interface Class {
  id: string;
  name: string;
}

export default function Classes() {
  const [classes, setClasses] = useState<Class[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchClasses = async () => {
      try {
        const token = localStorage.getItem('token');
        
        if (!token) {
          console.error('No auth token found');
          setLoading(false);
          return;
        }

        const response = await fetch('http://localhost:8080/classes/my-classes', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          if (response.status === 401) {
            console.error('token falhou');
          } else {
            console.error('ERRO');
          }
          setLoading(false);
          return;
        }

        const data = await response.json();
        setClasses(data);
        setLoading(false);
      } catch (err) {
        console.error('Erro ao buscar turmas:', err);
        setLoading(false);
      }
    };

    fetchClasses();
  }, []);

  if (loading) {
    return (
      <div className="classes-container">
        <div className="classes-content">
          <h1 className="classes-title">Suas turmas:</h1>
          <p className="classes-loading">Carregando turmas...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="classes-container">
        <TeacherSideBar />
      <div className="classes-content">
        <h1 className="classes-title">Suas turmas:</h1>
        
        {classes.length === 0 ? (
          <p className="classes-empty">Você não está matriculado em nenhuma turma.</p>
        ) : (
          <div className="classes-grid">
            {classes.map((classItem) => (
              <ClassesCard 
                key={classItem.id} 
                name={classItem.name}
                id={classItem.id}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};