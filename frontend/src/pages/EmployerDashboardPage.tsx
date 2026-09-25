import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  createEmployerJob,
  deleteEmployerJob,
  getEmployerJobs,
  EmployerJob,
} from "../api";
export default function EmployerDashboardPage() {
  const [jobs, setJobs] = useState<EmployerJob[]>([]);
  const [title, setTitle] = useState("");
  const [companyName, setCompanyName] = useState("");
  const [location, setLocation] = useState("");
  const [salary, setSalary] = useState("");
  const [description, setDescription] = useState("");
  useEffect(() => {
    getEmployerJobs().then(setJobs);
  }, []);
  const create = async (e: React.FormEvent) => {
    e.preventDefault();
    const job = await createEmployerJob({
      title,
      companyName,
      location,
      salary,
      description,
      deadline: "",
    });
    setJobs([...jobs, job]);
    setTitle("");
    setCompanyName("");
    setLocation("");
    setSalary("");
    setDescription("");
  };
  return (
    <main className="mx-auto max-w-6xl px-6 py-16">
      <h1 className="text-3xl font-bold">Employer dashboard</h1>
      <form onSubmit={create} className="mt-8 grid gap-3 md:grid-cols-2">
        <input
          required
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="rounded-xl border px-4 py-3"
          placeholder="Job title"
        />
        <input
          required
          value={companyName}
          onChange={(e) => setCompanyName(e.target.value)}
          className="rounded-xl border px-4 py-3"
          placeholder="Company"
        />
        <input
          required
          value={location}
          onChange={(e) => setLocation(e.target.value)}
          className="rounded-xl border px-4 py-3"
          placeholder="Location"
        />
        <input
          required
          value={salary}
          onChange={(e) => setSalary(e.target.value)}
          className="rounded-xl border px-4 py-3"
          placeholder="Salary"
        />
        <textarea
          required
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="rounded-xl border px-4 py-3 md:col-span-2"
          placeholder="Description"
        />
        <button className="button-primary md:col-span-2">Create job</button>
      </form>
      <div className="mt-10 space-y-3">
        {jobs.map((job) => (
          <div className="surface flex justify-between p-5" key={job.id}>
            <span>
              <b>{job.title}</b>
              <br />
              <small>
                {job.location} · {job.salary}
              </small>
            </span>
            <button
              onClick={async () => {
                await deleteEmployerJob(job.id);
                setJobs(jobs.filter((item) => item.id !== job.id));
              }}
              className="text-red-600"
            >
              Delete
            </button>
            <Link to={`/employer/jobs/${job.id}/edit`} className="text-blue-600">Edit</Link>
          </div>
        ))}
      </div>
    </main>
  );
}
