import request from './request'

export const registerUser = (data: {
  username: string
  email: string
  password: string
  fullName: string
  type: number

}) => request.post('/auth/register', data)

export const loginUser = (data: {
  identifier: string
  password: string
}) => request.post('/auth/login', data)

export async function getCurrentUser() {
  const res =  await request.get('/auth/me')
  return res
   
} 
export const addProject = (data:{
    name: string;
    description: string;
    status: number
}) => request.post('/projects/register', data)

export const logoutUser = () =>
  request.post('/auth/logout')
