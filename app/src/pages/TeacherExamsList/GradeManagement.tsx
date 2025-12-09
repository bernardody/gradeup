import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import './GradeManagement.css';
import TeacherSideBar from '../../Components/SideBar/Teacher-SideBar';

interface Student {
  id: number;
  name: string;
  email: string;
  type: string;
}

interface Grade {
  id: number;
  exam: Exam;
  student: Student;
  grade: number;
}

interface GradeInput {
  studentId: number;
  studentName: string;
  gradeId?: number;
  grade: string;
}

interface Exam {
  id: number;
  name: string;
  maxScore: number;
  examDate: string;
  classEntity: {
    id: number;
    name: string;
  };
  subject: {
    name: string;
  };
}

const GradeManagement: React.FC = () => {
  const { examId } = useParams<{ examId: string }>();
  const navigate = useNavigate();
  const location = useLocation();
  
  const [exam, setExam] = useState<Exam | null>(location.state?.exam || null);
  const [students, setStudents] = useState<Student[]>([]);
  const [grades, setGrades] = useState<GradeInput[]>([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    if (examId) {
      fetchData();
    }
  }, [examId]);

  const fetchData = async () => {
    try {
      setLoading(true);
      const token = localStorage.getItem('token');
      
      if (!token) {
        throw new Error('Token não encontrado. Faça login novamente.');
      }

      // Buscar notas existentes
      const gradesResponse = await fetch(`http://localhost:8080/grades/by-exam/${examId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!gradesResponse.ok) {
        throw new Error('Erro ao buscar notas');
      }

      const gradesData: Grade[] = await gradesResponse.json();
      
      // Se não temos o exam do state, pegar do primeiro grade
      if (!exam && gradesData.length > 0) {
        setExam(gradesData[0].exam as any);
      }

      // Buscar alunos da turma
      const classId = exam?.classEntity.id || gradesData[0]?.exam?.classEntity?.id;
      
      if (!classId) {
        throw new Error('ID da turma não encontrado');
      }

      const studentsResponse = await fetch(`http://localhost:8080/registrations/class/${classId}/students`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!studentsResponse.ok) {
        throw new Error('Erro ao buscar alunos');
      }

      const studentsData: Student[] = await studentsResponse.json();
      setStudents(studentsData);

      // Mapear notas existentes com todos os alunos
      const gradesMap = new Map(gradesData.map(g => [g.student.id, g]));
      
      const initialGrades: GradeInput[] = studentsData.map(student => {
        const existingGrade = gradesMap.get(student.id);
        return {
          studentId: student.id,
          studentName: student.name,
          gradeId: existingGrade?.id,
          grade: existingGrade ? existingGrade.grade.toString() : '',
        };
      });

      setGrades(initialGrades);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro ao carregar dados');
    } finally {
      setLoading(false);
    }
  };

  const handleGradeChange = (studentId: number, value: string) => {
    // Permitir vazio ou números decimais
    if (value === '' || /^\d*\.?\d*$/.test(value)) {
      setGrades(prev => prev.map(g => 
        g.studentId === studentId ? { ...g, grade: value } : g
      ));
    }
  };

  const validateGrade = (grade: string): boolean => {
    if (grade === '') return true; // Permitir vazio
    const numGrade = parseFloat(grade);
    return !isNaN(numGrade) && numGrade >= 0 && numGrade <= (exam?.maxScore || 0);
  };

  const handleSaveGrades = async () => {
    try {
      setSaving(true);
      setError(null);

      const token = localStorage.getItem('token');
      
      if (!token) {
        throw new Error('Token não encontrado. Faça login novamente.');
      }

      // Validar todas as notas
      const invalidGrades = grades.filter(g => g.grade !== '' && !validateGrade(g.grade));
      
      if (invalidGrades.length > 0) {
        throw new Error(`Notas inválidas detectadas. Verifique se todas estão entre 0 e ${exam?.maxScore}`);
      }

      // Processar cada nota
      const promises = grades
        .filter(g => g.grade !== '') // Apenas notas preenchidas
        .map(async (gradeInput) => {
          const gradeValue = parseFloat(gradeInput.grade);

          if (gradeInput.gradeId) {
            // Atualizar nota existente
            return fetch(`http://localhost:8080/grades/${gradeInput.gradeId}`, {
              method: 'PUT',
              headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
              },
              body: JSON.stringify({
                grade: gradeValue,
              }),
            });
          } else {
            // Criar nova nota
            return fetch('http://localhost:8080/grades', {
              method: 'POST',
              headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
              },
              body: JSON.stringify({
                examId: parseInt(examId!),
                studentId: gradeInput.studentId,
                grade: gradeValue,
              }),
            });
          }
        });

      const results = await Promise.all(promises);
      
      // Verificar se todas as requisições foram bem-sucedidas
      const failures = results.filter(r => !r.ok);
      
      if (failures.length > 0) {
        throw new Error(`Erro ao salvar ${failures.length} nota(s)`);
      }

      alert('Notas salvas com sucesso!');
      
      // Recarregar dados
      await fetchData();
      
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro ao salvar notas');
    } finally {
      setSaving(false);
    }
  };

  const filteredGrades = grades.filter(g => 
    g.studentName.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return <div className="grade-management__loading">Carregando...</div>;
  }

  if (!exam) {
    return <div className="grade-management__error">Prova não encontrada</div>;
  }

  return (
    <div className="grade-management">
      <div className="grade-management__header">
        <button 
          className="grade-management__back-button"
          onClick={() => navigate(-1)}
        >
          ← Voltar
        </button>
        
        <div className="grade-management__title">
          <h1>Gerenciar Notas</h1>
          <div className="grade-management__exam-info">
            <p><strong>Prova:</strong> {exam.name}</p>
            <p><strong>Turma:</strong> {exam.classEntity.name}</p>
            <p><strong>Disciplina:</strong> {exam.subject.name}</p>
            <p><strong>Nota máxima:</strong> {exam.maxScore.toFixed(2)}</p>
          </div>
        </div>
      </div>

      {error && (
        <div className="grade-management__error-banner">
          {error}
        </div>
      )}

      <div className="grade-management__controls">
        <input
          type="text"
          className="grade-management__search"
          placeholder="Pesquisar aluno..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        
        <button
          className="grade-management__save-button"
          onClick={handleSaveGrades}
          disabled={saving}
        >
          {saving ? 'Salvando...' : 'Salvar todas as notas'}
        </button>
      </div>

      <div className="grade-management__table-container">
        <table className="grade-management__table">
          <thead>
            <tr>
              <th>Aluno</th>
              <th>Email</th>
              <th>Nota</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {filteredGrades.map((gradeInput) => {
              const isValid = validateGrade(gradeInput.grade);
              const hasGrade = gradeInput.grade !== '';
              
              return (
                <tr key={gradeInput.studentId}>
                  <td>{gradeInput.studentName}</td>
                  <td>{students.find(s => s.id === gradeInput.studentId)?.email || '-'}</td>
                  <td>
                    <input
                      type="text"
                      className={`grade-management__input ${!isValid ? 'grade-management__input--invalid' : ''}`}
                      value={gradeInput.grade}
                      onChange={(e) => handleGradeChange(gradeInput.studentId, e.target.value)}
                      placeholder="0.00"
                      step="0.01"
                    />
                  </td>
                  <td>
                    <span className={`grade-management__status ${hasGrade ? 'grade-management__status--complete' : 'grade-management__status--pending'}`}>
                      {hasGrade ? '✓ Preenchida' : '○ Pendente'}
                    </span>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      <div className="grade-management__summary">
        <p>Total de alunos: {students.length}</p>
        <p>Notas lançadas: {grades.filter(g => g.grade !== '').length}</p>
        <p>Pendentes: {grades.filter(g => g.grade === '').length}</p>
      </div>
      <div className="sideBar">
          <TeacherSideBar />
        </div>
    </div>
  );
};

export default GradeManagement;