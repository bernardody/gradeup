import { useEffect, useState } from "react";
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip } from "recharts";

interface Subject {
  subjectName: string;
  trimesters: Array<{
    totalScore: number;
  }>;
}

interface StudentData {
  subjects: Subject[];
}

export default function Graphic() {
  const [data, setData] = useState<Array<{ name: string; nota: number }>>([]);

  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/dashboard", {
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then((studentData: StudentData) => {
        const chartData = studentData.subjects.map((subject) => {
          const notaTotal = subject.trimesters.reduce(
            (sum, trimester) => sum + trimester.totalScore,
            0
          );
          
          return {
            name: subject.subjectName,
            nota: notaTotal
          };
        });
        
        setData(chartData);
      })
      .catch(error => {
        console.error("Erro ao buscar dados:", error);
      });
  }, []);

  if (data.length === 0) {
    return <div style={{ padding: '20px', textAlign: 'center' }}>Ainda esta sem notas</div>;
  }

  return (
    <BarChart 
      width={900} 
      height={500} 
      data={data} 
      margin={{ top: 20, right: 30, left: 10, bottom: 120 }}
    >
      <CartesianGrid strokeDasharray="3 3" stroke="#e5e7eb" vertical={false} />
      <XAxis 
        dataKey="name" 
        axisLine={false}
        tickLine={false}
        tick={{ fill: '#6b7280', fontSize: 12, fontWeight: 500 }}
        angle={-45}
        textAnchor="end"
        height={100}
      />
      <YAxis 
        axisLine={false}
        tickLine={false}
        tick={{ fill: '#6b7280', fontSize: 12 }}
        domain={[0, 100]}
        ticks={[0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100]}
        width={50}
        label={{ 
          value: 'Nota total', 
          angle: -90, 
          position: 'insideLeft',
          style: { fill: '#374151', fontSize: 13, fontWeight: 600 }
        }}
      />
      <Tooltip 
        formatter={(value: number) => [`${value.toFixed(2)} pontos`, 'Nota Total']}
        contentStyle={{ 
          backgroundColor: '#ffffff', 
          border: '1px solid #e5e7eb',
          borderRadius: '8px',
          boxShadow: '0 4px 6px -1px rgb(0 0 0 / 0.1)',
          padding: '10px'
        }}
        cursor={{ fill: 'rgba(99, 102, 241, 0.1)' }}
      />
      <Bar 
        dataKey="nota" 
        fill="#6366f1"
        radius={[8, 8, 0, 0]}
        maxBarSize={70}
      />
    </BarChart>
  );
}