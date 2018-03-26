using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class syslogController : BaseApiController
    {
        // GET api/syslog
        public IEnumerable<syslog> Getsyslogs()
        {
            return db.syslog.AsEnumerable();
        }

        // GET api/syslog/5
        public syslog Getsyslog(long id)
        {
            syslog syslog = db.syslog.Find(id);
            if (syslog == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return syslog;
        }

        // PUT api/syslog/5
        public HttpResponseMessage Putsyslog(long id, syslog syslog)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != syslog.id)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(syslog).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // POST api/syslog
        public HttpResponseMessage Postsyslog(syslog syslog)
        {
            if (ModelState.IsValid)
            {
                db.syslog.Add(syslog);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, syslog);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = syslog.id }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/syslog/5
        public HttpResponseMessage Deletesyslog(long id)
        {
            syslog syslog = db.syslog.Find(id);
            if (syslog == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.syslog.Remove(syslog);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, syslog);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}