import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { getJobById, Job } from "../api";
export default function JobDetailsPage() {
  const { jobId } = useParams();
  const [job, setJob] = useState<Job>();
  const isEmployer = localStorage.getItem("jobconnect_role") === "EMPLOYER";
  const [errorMessage, setErrorMessage] = useState("");
  useEffect(() => {
    if (!jobId) return;
    getJobById(Number(jobId)).then(setJob).catch(() => setErrorMessage("This job could not be loaded."));
  }, [jobId]);
  if (errorMessage) return <main className="mx-auto max-w-4xl px-6 py-16"><p className="text-red-600">{errorMessage}</p><Link to="/">Return to jobs</Link></main>;
  if (!job)
    return <main className="mx-auto max-w-4xl px-6 py-16">Loading job…</main>;
  return (
    <main className="mx-auto max-w-4xl px-6 py-16">
      <p className="font-bold uppercase text-blue-600">{job.companyName}</p>
      <h1 className="mt-3 text-4xl font-bold">{job.title}</h1>
      <p className="mt-6 leading-7 text-slate-600">{job.description}</p>
      <p className="mt-5 text-slate-500">
        {job.location} · {job.salary}
      </p>
      {!isEmployer && <Link
        to={`/jobs/${job.id}/apply`}
        className="button-primary mt-8 inline-block"
      >
        Apply now
      </Link>}
    </main>
  );
}
