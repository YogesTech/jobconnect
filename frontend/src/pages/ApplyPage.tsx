import { FormEvent, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { submitApplication } from "../api";
export default function ApplyPage() {
  const { jobId } = useParams();
  const navigate = useNavigate();
  const [coverLetter, setCoverLetter] = useState("");
  const [resumeLink, setResumeLink] = useState("");
  const [error, setError] = useState("");
  const submit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await submitApplication(Number(jobId), coverLetter, resumeLink);
      navigate("/applications");
    } catch {
      setError("Please sign in before applying.");
    }
  };
  return (
    <main className="mx-auto max-w-2xl px-6 py-16">
      <h1 className="text-3xl font-bold">Apply for job #{jobId}</h1>
      <form onSubmit={submit} className="mt-8 space-y-4">
        <textarea
          required
          minLength={20}
          value={coverLetter}
          onChange={(e) => setCoverLetter(e.target.value)}
          className="min-h-40 w-full rounded-xl border px-4 py-3"
          placeholder="Cover letter"
        />
        <input
          required
          type="url"
          value={resumeLink}
          onChange={(e) => setResumeLink(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
          placeholder="Resume URL"
        />
        {error && <p className="text-red-600">{error}</p>}
        <button className="button-primary">Submit application</button>
      </form>
    </main>
  );
}
