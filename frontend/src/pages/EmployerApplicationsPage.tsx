import { useEffect, useState } from "react";
import {
  getEmployerApplications,
  EmployerApplication,
  updateApplicationStatus,
} from "../api";
export default function EmployerApplicationsPage() {
  const [applications, setApplications] = useState<EmployerApplication[]>([]);
  useEffect(() => {
    getEmployerApplications().then(setApplications);
  }, []);
  return (
    <main className="mx-auto max-w-6xl px-6 py-16">
      <h1 className="text-3xl font-bold">Application review</h1>
      <div className="mt-8 space-y-4">
        {applications.map((application) => (
          <article className="surface p-5" key={application.id}>
            <div className="flex justify-between">
              <h2 className="font-bold">{application.jobTitle}</h2>
              <select
                value={application.status}
                onChange={async (e) => {
                  const updated = await updateApplicationStatus(
                    application.id,
                    e.target.value,
                  );
                  setApplications(
                    applications.map((item) =>
                      item.id === updated.id ? updated : item,
                    ),
                  );
                }}
                className="rounded-lg border px-3 py-2"
              >
                <option>APPLIED</option>
                <option>REVIEWED</option>
                <option>SHORTLISTED</option>
                <option>REJECTED</option>
                <option>ACCEPTED</option>
              </select>
            </div>
            <p className="mt-2 text-sm text-slate-500">
              Applicant: {application.applicantUsername}
            </p>
            <p className="mt-4 text-slate-600">{application.coverLetter}</p>
            <a
              className="mt-3 inline-block text-blue-600"
              href={application.resumeLink}
              target="_blank"
            >
              View resume
            </a>
          </article>
        ))}
      </div>
    </main>
  );
}
