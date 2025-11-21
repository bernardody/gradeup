import "./GradeBox.css";

interface GradeBoxProps {
  label: string;
  value: number;
  min?: number;
  max?: number;
}

export default function GradeBox({ label, value, min, max }: GradeBoxProps) {
  return (
    <div className="grade-box">
      <p><strong>{label}:</strong> {value}</p>
      {(min !== undefined && max !== undefined) && (
        <span className="sub">
          (min: {min}; max: {max})
        </span>
      )}
    </div>
  );
}
