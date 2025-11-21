import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip } from "recharts";

const data = [
  { name: 'Jan', vendas: 30 },
  { name: 'Fev', vendas: 45 },
  { name: 'Mar', vendas: 60 },
  { name: 'Abr', vendas: 40 },
  { name: 'Mai', vendas: 80 },
];

export default function Graphic() {
  return (
    <BarChart width={800} height={300} data={data} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
      <CartesianGrid strokeDasharray="0" stroke="#e0e0e0" vertical={false} />
      <XAxis 
        dataKey="name" 
        axisLine={false}
        tickLine={false}
        tick={{ fill: '#666', fontSize: 12 }}
      />
      <YAxis 
        axisLine={false}
        tickLine={false}
        tick={{ fill: '#666', fontSize: 12 }}
      />
      <Tooltip />
      <Bar 
        dataKey="vendas" 
        fill="#c0c0c0"
        radius={[4, 4, 0, 0]}
        barSize={40}
      />
    </BarChart>
  );
}