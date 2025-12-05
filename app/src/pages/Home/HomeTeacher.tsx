import TeacherSideBar from "../../Components/SideBar/Teacher-SideBar";
import HomeBase from "../../Components/HomeBase/HomeBase";
import 'react-big-calendar/lib/css/react-big-calendar.css'
import "./css/HomeTeacher.css";
import { Calendar, dateFnsLocalizer } from 'react-big-calendar';
import { format, parse, startOfWeek, getDay } from 'date-fns';
import { ptBR } from 'date-fns/locale';
;
import { useState, useEffect } from 'react';

const locales = {
  'pt-BR': ptBR,
};

const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek,
  getDay,
  locales,
});

interface ExamAPI {
    id: number;
    classEntity: {
        id: number;
        name: string;
        year: number;
    };
    subject: {
        id: number;
        name: string;
    };
    teacher: {
        id: number;
        name: string;
        email: string;
        type: string;
    };
    trimester: {
        id: number;
        name: string;
        maxPoints: number;
        minPoints: number;
    };
    name: string;
    maxScore: number;
    examDate: string;
}

interface EventoCalendario {
    id: number;
    title: string;
    start: Date;
    end: Date;
    resource?: {
        subject: string;
        class: string;
        maxScore: number;
        trimester: string;
    };
}

export default function HomeTeacher() {
    const [events, setEvents] = useState<EventoCalendario[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [currentDate, setCurrentDate] = useState(new Date());

    useEffect(() => {
        const fetchAllExams = async () => {
            try {
                setLoading(true);

                const response = await fetch(
                    `http://localhost:8080/exams/teacher/all`,
                    {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem('token')}`
                        }
                    }
                );

                if (!response.ok) {
                    throw new Error('Erro ao buscar provas');
                }

                const data: ExamAPI[] = await response.json();

                const eventosFormatados: EventoCalendario[] = data.map(exam => {
                    const [year, month, day] = exam.examDate.split('-').map(Number);
                    const examDate = new Date(year, month - 1, day);

                    return {
                        id: exam.id,
                        title: `${exam.name} - ${exam.subject.name}`,
                        start: examDate,
                        end: examDate,
                        resource: {
                            subject: exam.subject.name,
                            class: exam.classEntity.name,
                            maxScore: exam.maxScore,
                            trimester: exam.trimester.name
                        }
                    };
                });

                setEvents(eventosFormatados);
                setError(null);

            } catch (err) {
                console.error("Erro ao carregar provas:", err);
                setError("Não foi possível carregar as provas. Tente novamente mais tarde.");
            } finally {
                setLoading(false);
            }
        };

        fetchAllExams();
    }, []);

    const eventStyleGetter = () => {
        return {
            style: {
                backgroundColor: '#3174ad',
                borderRadius: '5px',
                opacity: 0.9,
                color: 'white',
                border: '0px',
                display: 'block',
                padding: '5px',
                fontSize: '12px',
                fontWeight: '500'
            }
        };
    };

    const handleSelectEvent = (event: EventoCalendario) => {
        const info = `
📝 ${event.title}
👥 Turma: ${event.resource?.class}
📚 Matéria: ${event.resource?.subject}
⭐ Nota Máxima: ${event.resource?.maxScore}
📅 ${event.resource?.trimester}
🗓️ Data: ${format(event.start, "dd 'de' MMMM 'de' yyyy", { locale: ptBR })}
        `;
        alert(info);
    };

    const handleNavigate = (newDate: Date) => {
        setCurrentDate(newDate);
    };

    if (loading) {
        return (
            <div className="hometeacher">
                <div className="sideBar">
                    <TeacherSideBar />
                </div>
                <div className="main-content">
                    <div className="base">
                        <HomeBase />
                        <div className="studentInfo">
                            <p>⏳ Carregando seu calendário de provas...</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="hometeacher">
                <div className="sideBar">
                    <TeacherSideBar />
                </div>
                <div className="main-content">
                    <div className="base">
                        <HomeBase />
                        <div className="studentInfo">
                            <p style={{ color: 'red' }}>❌ {error}</p>
                            <button onClick={() => window.location.reload()}>
                                Tentar novamente
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="hometeacher">
            <div className="sideBar">
                <TeacherSideBar />
            </div>
            <div className="main-content">
                <div className="base">
                    <HomeBase />
                    <div className="studentInfo">
                        <p>📅 Suas Provas • Total: {events.length}</p>
                    </div>
                </div>
                <div className="calendar-container">
                    <div className="calendar">
                        <Calendar
                            localizer={localizer}
                            events={events}
                            startAccessor="start"
                            endAccessor="end"
                            date={currentDate}
                            onNavigate={handleNavigate}
                            style={{ height: 600 }}
                            culture="pt-BR"
                            eventPropGetter={eventStyleGetter}
                            onSelectEvent={handleSelectEvent}
                            messages={{
                                next: "Próximo",
                                previous: "Anterior",
                                today: "Hoje",
                                month: "Mês",
                                week: "Semana",
                                day: "Dia",
                                agenda: "Agenda",
                                date: "Data",
                                time: "Hora",
                                event: "Evento",
                                noEventsInRange: "Nenhuma prova neste período"
                            }}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}