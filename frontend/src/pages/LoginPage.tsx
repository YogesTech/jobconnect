import { FormEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../api";
export default function LoginPage() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const submit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await login(username, password);
      navigate("/");
    } catch {
      setError("Invalid username or password.");
    }
  };
  return (
    <main className="mx-auto max-w-md px-6 py-16">
      <h1 className="text-3xl font-bold">Welcome back</h1>
      <p className="mt-2 text-slate-500">
        Sign in to apply and manage your profile.
      </p>
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
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
          type="password"
          placeholder="Password"
        />
        {error && <p className="text-sm text-red-600">{error}</p>}
        <button className="button-primary w-full">Sign in</button>
      </form>
      <p className="mt-6 text-center text-sm text-slate-500">New to JobConnect? <Link className="font-semibold text-blue-600" to="/register">Create an account</Link></p>
    </main>
  );
}
