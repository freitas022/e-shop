import axios from 'axios';
import { BACKEND_URL } from '../util/request';

export function authenticate(request: { email: string, password: string }) {
    return axios.post(`${BACKEND_URL}/auth/authenticate`,
        request,
        { withCredentials: true }
    );
}