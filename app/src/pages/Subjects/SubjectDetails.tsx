import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import StudentSideBar from "../../Components/SideBar/Student-SideBar";
import "../Subjects/css/SubjectDetails.css";
import GradeBox from "../../Components/Gradebox/Gradebox";

interface GradeItem {
  label: string;
  value: number;
  min: number;
  max: number;
}

interface SubjectDetailsData {
  subjectName: string;
  total: number;
  minTotal: number;
  maxTotal: number;
  tests: GradeItem[];
  tasks: GradeItem[];
  totalScore: number;
}

export default function SubjectDetails() {
  const { subjectName } = useParams();
  const [tri, setTri] = useState(1);
  const [details, setDetails] = useState<SubjectDetailsData | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/dashboard", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => res.json())
      .then(data => {
        // achar a matéria pelo nome
        const subject = data.subjects.find(
          (s: any) =>
            s.subjectName.toLowerCase() === subjectName?.toLowerCase()
        );

        if (!subject) {
          console.log("Matéria não encontrada");
          return;
        }

        // pegar o trimestre certo (tri-1 porque array começa do 0)
        const triData = subject.trimesters[tri - 1];

        //  converter EXAMS → tests
        const tests = triData.exams.map((exam: any) => ({
          label: exam.examName,
          value: exam.studentGrade,
          min: 0,
          max: exam.maxScore,
        }));

        //  se tiver trabalhos no backend, converte aqui
        const tasks = triData.tasks
          ? triData.tasks.map((t: any) => ({
              label: t.taskName,
              value: t.studentGrade,
              min: 0,
              max: t.maxScore,
            }))
          : [];

        //  montar objeto final
        setDetails({
          subjectName: subject.subjectName,
          total: triData.maxPoints,
          minTotal: triData.minPoints,
          maxTotal: triData.maxPoints,
          tests,
          tasks,
          totalScore: triData.totalScore,
        });
      })
      .catch(err => console.log("Erro:", err));
  }, [subjectName, tri]);

  if (!details) return <h2>Carregando...</h2>;

  return (
    <div className="details-page">
      <div className="sidebar">
        <StudentSideBar />
      </div>

      <div className="content">
        <h1>{details.subjectName}</h1>

        {/* Trimestres */}
        <div className="trimester-selector">
          {[1, 2, 3].map(num => (
            <button
              key={num}
              className={tri === num ? "tri active" : "tri"}
              onClick={() => setTri(num)}
            >
              {num}° tri
            </button>
          ))}
        </div>

        <h3 className="desc">Suas notas:</h3>

        <h4 className="desc">
          Total em {details.subjectName.toLowerCase()}: {details.total}
          <br />
          <span className="mini">
            (min: {details.minTotal}; max: {details.maxTotal})
          </span>
        </h4>

        <div className="grades-container">
          {/* PROVAS */}
          <div className="column">
            <h3>Provas</h3>
            {details.tests.map((t, i) => (
              <GradeBox
                key={i}
                label={t.label}
                value={t.value}
                min={t.min}
                max={t.max}
              />
            ))}
            <p className="result">Total:{details.totalScore}</p>
          </div>

          {/* TRABALHOS */}
          <div className="column">
            <h3>Trabalhos</h3>
            {details.tasks.length === 0 ? (
              <p>Sem trabalhos neste trimestre</p>
            ) : (
              details.tasks.map((t, i) => (
                <GradeBox
                  key={i}
                  label={t.label}
                  value={t.value}
                  min={t.min}
                  max={t.max}
                />
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
