import { useEffect, useState } from "react";
import { getMyApplications, ApplicationSummary } from "../api";
export default function MyApplicationsPage() {
  const [applications, setApplications] = useState<ApplicationSummary[]>([]);
  useEffect(() => {
    getMyApplications()
      .then(setApplications)
      .catch(() => undefined);
  }, []);
  return (
    <main className="mx-auto max-w-5xl px-6 py-16">
      <h1 className="text-3xl font-bold">My applications</h1>
      <div className="mt-8 space-y-3">
        {applications.map((application) => (
          <div
            className="surface flex justify-between p-5"
            key={application.id}
          >
            <span className="font-semibold">{application.jobTitle}</span>
            <span className="text-blue-700">{application.status}</span>
          </div>
        ))}
      </div>
    </main>
  );
}
