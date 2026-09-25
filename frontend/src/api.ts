export type Job = {
  id: number;
  title: string;
  companyName: string;
  description: string;
  location: string;
  deadline: string;
  salary: string;
};
const apiBaseUrl =
  import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";
let slowRequestTimer: number | undefined;
export async function request<T>(
  path: string,
  options?: RequestInit,
): Promise<T> {
  slowRequestTimer = window.setTimeout(
    () => window.dispatchEvent(new Event("backend-slow")),
    3500,
  );
  try {
    const token = localStorage.getItem("jobconnect_access_token");
    const response = await fetch(`${apiBaseUrl}${path}`, {
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
        ...options?.headers,
      },
      ...options,
    });
    if (response.status === 401) {
      localStorage.removeItem("jobconnect_access_token");
      localStorage.removeItem("jobconnect_role");
    }
    if (!response.ok) throw new Error(`Request failed (${response.status})`);
    return response.status === 204 ? (undefined as T) : response.json();
  } finally {
    window.clearTimeout(slowRequestTimer);
  }
}
export const getJobs = (searchKeyword: string) =>
  request<Job[]>(
    `/jobs${searchKeyword ? `?search=${encodeURIComponent(searchKeyword)}` : ""}`,
  );
export const getJobById = (jobId: number) => request<Job>(`/jobs/${jobId}`);
export type AuthenticatedUser = { id: number; username: string; role: string };
export const login = async (username: string, password: string) => {
  const response = await request<{ token: string; user: AuthenticatedUser }>(
    "/auth/login",
    { method: "POST", body: JSON.stringify({ username, password }) },
  );
  localStorage.setItem("jobconnect_access_token", response.token);
  localStorage.setItem("jobconnect_role", response.user.role);
  window.dispatchEvent(new Event("auth-changed"));
  return response.user;
};
export const submitApplication = (
  jobId: number,
  coverLetter: string,
  resumeLink: string,
) =>
  request("/applications", {
    method: "POST",
    body: JSON.stringify({ jobId, coverLetter, resumeLink }),
  });
export const signup = (
  username: string,
  password: string,
  role = "JOB_SEEKER",
) =>
  request<AuthenticatedUser>("/auth/signup", {
    method: "POST",
    body: JSON.stringify({ username, password, role }),
  });
export type ApplicationSummary = {
  id: number;
  jobId: number;
  jobTitle: string;
  status: string;
};
export const getMyApplications = () =>
  request<ApplicationSummary[]>("/applications/mine");
export const getCurrentUser = () =>
  request<AuthenticatedUser | undefined>("/auth/me");
export const logout = async () => {
  try {
    await request<void>("/auth/logout", { method: "POST" });
  } finally {
    localStorage.removeItem("jobconnect_access_token");
    localStorage.removeItem("jobconnect_role");
    window.dispatchEvent(new Event("auth-changed"));
  }
};
export type EmployerJob = {
  id: number;
  title: string;
  companyName: string;
  location: string;
  salary: string;
};
export const getEmployerJobs = () => request<EmployerJob[]>("/employer/jobs");
export const getEmployerJob = async (jobId: number) => (await getEmployerJobs()).find((job) => job.id === jobId);
export const createEmployerJob = (job: Record<string, string>) =>
  request<EmployerJob>("/employer/jobs", {
    method: "POST",
    body: JSON.stringify(job),
  });
export const deleteEmployerJob = (id: number) =>
  request<void>(`/employer/jobs/${id}`, { method: "DELETE" });
export const updateEmployerJob = (id: number, job: Record<string, string>) =>
  request<EmployerJob>(`/employer/jobs/${id}`, {
    method: "PUT",
    body: JSON.stringify(job),
  });
export type EmployerApplication = {
  id: number;
  jobId: number;
  jobTitle: string;
  applicantUsername: string;
  coverLetter: string;
  resumeLink: string;
  status: string;
};
export const getEmployerApplications = () =>
  request<EmployerApplication[]>("/employer/applications");
export const updateApplicationStatus = (id: number, status: string) =>
  request<EmployerApplication>(`/employer/applications/${id}/status`, {
    method: "PATCH",
    body: JSON.stringify({ status }),
  });
