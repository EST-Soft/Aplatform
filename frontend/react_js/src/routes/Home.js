/* Home.js */
import React from 'react';

// import JobPostings from '../components/JobPosting';
import QnA from '../components/QnA';
import Feedback from '../components/Feedback';
import '../styles/Home.css'

const Home = () => {
  return (
    <div className='main-content'>
      {/*<JobPostings />*/}
      <div className='sections'>
        <QnA />
        <Feedback />
      </div>
    </div>
  );
};

export default Home;