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
    public class sysparamsController : BaseApiController
    {
        // GET api/sysparams
        public IEnumerable<sysparams> Getsysparams()
        {
            return db.sysparams.AsEnumerable();
        }

        // GET api/sysparams/5
        public sysparams Getsysparams(int id)
        {
            sysparams sysparams = db.sysparams.Find(id);
            if (sysparams == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return sysparams;
        }

        // PUT api/sysparams/5
        public HttpResponseMessage Putsysparams(int id, sysparams sysparams)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != sysparams.id)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(sysparams).State = EntityState.Modified;

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

        // POST api/sysparams
        public HttpResponseMessage Postsysparams(sysparams sysparams)
        {
            if (ModelState.IsValid)
            {
                db.sysparams.Add(sysparams);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, sysparams);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = sysparams.id }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/sysparams/5
        public HttpResponseMessage Deletesysparams(int id)
        {
            sysparams sysparams = db.sysparams.Find(id);
            if (sysparams == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.sysparams.Remove(sysparams);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, sysparams);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}