import http from 'k6/http';
import { check, sleep } from 'k6';

const raffleId = __ENV.RAFFLEID;
export let options = {
    vus: 10,
    duration : '5s'
};

export default function () {
    // let userId = Math.floor(Math.random() * 10)+1;
    let jwt= __ENV["JWT"]
    let url = `http://localhost:8080/api/v1/raffle/purchase/`+raffleId
    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+jwt,
        },
    };
    let res = http.post(url,{},params);
    console.log(res.status)
    console.log(url)
    console.log(jwt)
    check(res, {
        'is status 200': (r) => r.status === 200,
        'response time < 500ms': (r) => r.timings.duration < 50000,
    });
    sleep(1);
}