const authHeader = () => {
  const user = JSON.parse(localStorage.getItem('user'));

  if (user && user.jwtToken) {
    return {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + user.jwtToken
    }; // for Spring Boot back-end
    // return { 'x-access-token': user.accessToken };       // for Node.js Express back-end
  } else {
    return {};
  }
}

export default authHeader