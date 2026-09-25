import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getEmployerJob, updateEmployerJob } from "../api";
export default function EditJobPage() {
  const { jobId } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [companyName, setCompanyName] = useState("");
  const [location, setLocation] = useState("");
  const [salary, setSalary] = useState("");
  const [description, setDescription] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");
  useEffect(() => { if (!jobId) return; getEmployerJob(Number(jobId)).then((job) => { if (!job) { setErrorMessage("Job not found."); return; } setTitle(job.title); setCompanyName(job.companyName); setLocation(job.location); setSalary(job.salary); setIsLoading(false); }).catch(() => setErrorMessage("Unable to load this job.")); }, [jobId]);
  const save = async (e: React.FormEvent) => {
    e.preventDefault();
    await updateEmployerJob(Number(jobId), {
      title,
      companyName,
      location,
      salary,
      description,
      deadline: "",
    });
    navigate("/employer");
  };
  if (isLoading && !errorMessage) return <main className="mx-auto max-w-2xl px-6 py-16">Loading job…</main>;
  if (errorMessage) return <main className="mx-auto max-w-2xl px-6 py-16 text-red-600">{errorMessage}</main>;
  return (
    <main className="mx-auto max-w-2xl px-6 py-16">
      <h1 className="text-3xl font-bold">Edit job</h1>
      <form onSubmit={save} className="mt-8 space-y-4">
        <input
          required
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
          placeholder="Job title"
        />
        <input
          required
          value={companyName}
          onChange={(e) => setCompanyName(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
          placeholder="Company"
        />
        <input
          required
          value={location}
          onChange={(e) => setLocation(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
          placeholder="Location"
        />
        <input
          required
          value={salary}
          onChange={(e) => setSalary(e.target.value)}
          className="w-full rounded-xl border px-4 py-3"
          placeholder="Salary"
        />
        <textarea
          required
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="min-h-40 w-full rounded-xl border px-4 py-3"
          placeholder="Description"
        />
        <button className="button-primary">Save changes</button>
      </form>
    </main>
  );
}
