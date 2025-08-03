import request from "../request";



export const getTeams = (id:string) => request.get(`projects/${id}/team`)