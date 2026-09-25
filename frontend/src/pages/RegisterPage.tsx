import { FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { signup } from "../api";
export default function RegisterPage() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("JOB_SEEKER");
  const [error, setError] = useState("");
  const submit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await signup(username, password, role);
      navigate("/login");
    } catch {
      setError("Unable to create account.");
    }
  };
  return (
    <main className="mx-auto max-w-md px-6 py-16">
      <h1 className="text-3xl font-bold">Create account</h1>
      <form onSubmit={submit} className="mt-8 space-y-4">
        <input
          required
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
          placeholder="Username"
        />
        <input
          required
          minLength={8}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
          type="password"
          placeholder="Password"
        />
        <select
          value={role}
          onChange={(e) => setRole(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
        >
          <option>JOB_SEEKER</option>
          <option>EMPLOYER</option>
        </select>
        {error && <p className="text-sm text-red-600">{error}</p>}
        <button className="button-primary w-full">Register</button>
      </form>
    </main>
  );
}
